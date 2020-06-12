package com.devil.mall.controller;

import com.devil.mall.consts.MallConst;
import com.devil.mall.form.ShippingForm;
import com.devil.mall.pojo.User;
import com.devil.mall.service.IShippingService;
import com.devil.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Hanyanjiao
 * @date 2020/5/27
 */
@RestController
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    /**
     * 添加收货地址
     * @param shippingForm
     * @param session
     * @return
     */
    @PostMapping("/shippings")
    public ResponseVo add(@Valid @RequestBody ShippingForm shippingForm, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(),shippingForm);
    }

    /**
     * 删除收货地址
     * @param shippingId
     * @param session
     * @return
     */
    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable Integer shippingId, HttpSession session){
        User user  = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }

    /**
     * 修改收货地址
     * @param shippingId
     * @param form
     * @param session
     * @return
     */
    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm form,
                             HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(),shippingId,form);
    }

    /**
     * 收货地址列表
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @GetMapping("/shippings")
    public ResponseVo list(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                           HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}
