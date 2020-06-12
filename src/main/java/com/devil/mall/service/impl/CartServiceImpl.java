package com.devil.mall.service.impl;

import com.devil.mall.dao.ProductMapper;
import com.devil.mall.enums.ProductStatusEnum;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.form.CartAddForm;
import com.devil.mall.form.CartUpdateForm;
import com.devil.mall.pojo.Cart;
import com.devil.mall.pojo.Product;
import com.devil.mall.service.ICartService;
import com.devil.mall.vo.CartProductVo;
import com.devil.mall.vo.CartVo;
import com.devil.mall.vo.ResponseVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Hanyanjiao
 * @date 2020/5/26
 */

@Service
public class CartServiceImpl implements ICartService {
    public static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm addForm) {

        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey(addForm.getProductId());

        //判断商品是否存在
        if(product == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品状态是否是在售状态
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否充足
        if(product.getStock() < 0){
            return ResponseVo.error(ResponseEnum.PRODUCT_UNDER_STOCK);
        }

        //写入到redis
        //key: cart_uid
        Cart cart;

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey,String.valueOf(product.getId()));

        if(StringUtils.isEmpty(value)){
            //没有该商品，新增
            cart = new Cart(product.getId(), quantity, true);
        }else{
            //存在该商品，数量加1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity()+quantity);

        }
        opsForHash.put(redisKey,String.valueOf(product.getId()),
                gson.toJson(cart));
        
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;

        Integer totalQuantity = 0;

        BigDecimal totalPrice = BigDecimal.valueOf(0);

        CartVo cartVo = new CartVo();

        List<CartProductVo> cartProductVos = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);

            //TODO 使用mysql里的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if(product != null){
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getStatus(),
                        product.getPrice(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getSelected());

                cartProductVos.add(cartProductVo);
                if(!cart.getSelected()){
                    selectAll = false;
                }

                //计算出来的是选中的商品数量
                if(cart.getSelected()){
                    totalPrice = totalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
            totalQuantity += cart.getQuantity();
        }

        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setCartTotalQuantity(totalQuantity);
        cartVo.setCartProductVoList(cartProductVos);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm updateForm) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey,String.valueOf(productId));

        if(StringUtils.isEmpty(value)){
            //购物车没有该商品
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //存在该商品
        Cart cart = gson.fromJson(value, Cart.class);
        if(updateForm.getQuantity() !=null && updateForm.getQuantity()>=0){
            cart.setQuantity(updateForm.getQuantity());
        }

        if(updateForm.getSelected() !=null){
            cart.setSelected(updateForm.getSelected());
        }
        opsForHash.put(redisKey,String.valueOf(productId),gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        String value = opsForHash.get(redisKey,String.valueOf(productId));

        if(StringUtils.isEmpty(value)){
            //购物车没有该商品
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //存在该商品
        opsForHash.delete(redisKey,String.valueOf(productId));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        List<Cart> carts = listForCart(uid);

        for (Cart cart : carts) {
            cart.setSelected(true);
            opsForHash.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        List<Cart> carts = listForCart(uid);

        for (Cart cart : carts) {
            cart.setSelected(false);
            opsForHash.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> cartSum(Integer uid) {
        Integer sum = listForCart(uid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        return ResponseVo.success(sum);

    }

    public List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> carts = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            carts.add(gson.fromJson(entry.getValue(),Cart.class));
        }
        return carts;
    }
}
