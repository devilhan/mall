package com.devil.mall.service.impl;

import com.devil.mall.dao.OrderItemMapper;
import com.devil.mall.dao.OrderMapper;
import com.devil.mall.dao.ProductMapper;
import com.devil.mall.dao.ShippingMapper;
import com.devil.mall.enums.OrderStatusEnum;
import com.devil.mall.enums.PaymentTypeEnum;
import com.devil.mall.enums.ProductStatusEnum;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.pojo.*;
import com.devil.mall.service.ICartService;
import com.devil.mall.service.IOrderService;
import com.devil.mall.vo.OrderItemVo;
import com.devil.mall.vo.OrderVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Hanyanjiao
 * @date 2020/5/28
 */

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //校验收货地址
        Shipping shipping = shippingMapper.selectByUidAndId(uid,shippingId);

        if (shipping == null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }

        //获取购物车
        List<Cart> carts = cartService.listForCart(uid).stream()
                .filter(Cart::getSelected).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(carts)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        //获取products
        Set<Integer> productIdSet = carts.stream().map(Cart::getProductId).collect(Collectors.toSet());

        List<Product> products = productMapper.selectByProductIdSet(productIdSet);

        Map<Integer,Product> map = products.stream()
                .collect(Collectors.toMap(Product::getId,product -> product));

        List<OrderItem> orderItems = new ArrayList<>();
        Long orderNo = generateOrderNo();

        for (Cart cart : carts) {
            //根据productId 查询数据库
            Product product = map.get(cart.getProductId());
            //是否存在商品
            if(product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,"商品不存在: productId = "+cart.getProductId());
            }
            //商品上架状态
            if(!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,"商品不是待售状态"+product.getName());
            }

            //判断库存
            if(product.getStock() < cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_UNDER_STOCK,"库存不足"+product.getName());
            }

            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);

            orderItems.add(orderItem);

            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if(row <= 0){
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }


        //计算总价，只计算被选中的商品

        //生成订单 入库:order orderItem  //事务

        Order order = buildOrder(uid, orderNo, shippingId, orderItems);

        int row = orderMapper.insertSelective(order);

        if(row<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        int rowForOrder = orderItemMapper.batchInsert(orderItems);

        if(rowForOrder <= 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        //更新购物车(操作redis )
        for (Cart cart : carts) {
            cartService.delete( uid,cart.getProductId());
        }
        //构造vo
        OrderVo orderVo = buildOrderVo(order, orderItems, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.selectByUid(uid);
        Set<Long> orderNoSet = orders.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());

        List<OrderItem> orderItems = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Map<Long,List<OrderItem>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> shippingIdSet = orders.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());

        List<Shipping> shippings = shippingMapper.selectByIdSet(shippingIdSet);

        Map<Integer,Shipping> shippingMap = shippings.stream()
                .collect(Collectors.toMap(Shipping::getId,shipping->shipping));

        List<OrderVo> orderVos = new ArrayList<>();
        for (Order order : orders) {
            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVos.add(orderVo);
        }

        PageInfo pageInfo = new PageInfo(orders);
        pageInfo.setList(orderVos);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItems, shipping);

        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //只有为付款订单才能取消，根据自己公司业务
        if(!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());

        int row = orderMapper.updateByPrimaryKeySelective(order);
        if(row <= 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null ){
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc()+"订单id:"+orderNo);
        }
        //只有未付款订单才可以更新为已付款
        if(!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getDesc()+"订单id:"+orderNo);
        }

        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());

        int row = orderMapper.updateByPrimaryKeySelective(order);
        if(row <= 0){
            throw new RuntimeException("将订单更新为已支付状态失败，订单id："+orderNo);
        }
    }

    private OrderVo buildOrderVo(Order order,List<OrderItem> orderItems,Shipping shipping){
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order,orderVo);

        List<OrderItemVo> orderItemVos = orderItems.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());

        orderVo.setOrderItemVos(orderItemVos);
        if(shipping !=null ){
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }
        return orderVo;
    }

    private Order buildOrder(Integer uid,Long orderNo,Integer shippingId,
                             List<OrderItem> orderItems) {
        BigDecimal payment = orderItems.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());

        return order;
    }

    /**
     * 企业级：分布式唯一id/主键
     * @return
     */
    private Long generateOrderNo() {
        return System.currentTimeMillis()+new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid,Long orderNo,Integer quantity,Product product) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductImage(product.getMainImage());
        item.setProductName(product.getName());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        return item;
    }
}
