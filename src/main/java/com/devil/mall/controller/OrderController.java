package com.devil.mall.controller;

import com.devil.mall.consts.MallConst;
import com.devil.mall.form.OrderCreateForm;
import com.devil.mall.pojo.User;
import com.devil.mall.service.IOrderService;
import com.devil.mall.vo.OrderVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Hanyanjiao
 * @date 2020/6/2
 */
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 用户下单
     * @param createForm
     * @param session
     * @return
     */
    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm createForm,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(),createForm.getShippingId());
    }

    /**
     * 订单列表
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(),pageNum,pageSize);
    }

    /**
     * 订单详情
     * @param orderNo
     * @param session
     * @return
     */
    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable Long orderNo,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(),orderNo);
    }

    /**
     * 取消订单
     * @param orderNo
     * @param session
     * @return
     */
    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo,
                             HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(),orderNo);
    }
}
