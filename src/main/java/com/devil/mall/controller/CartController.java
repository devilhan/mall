package com.devil.mall.controller;

import com.devil.mall.consts.MallConst;
import com.devil.mall.form.CartAddForm;
import com.devil.mall.form.CartUpdateForm;
import com.devil.mall.pojo.User;
import com.devil.mall.service.ICartService;
import com.devil.mall.vo.CartVo;
import com.devil.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 购物车
 * @author Hanyanjiao
 * @date 2020/5/26
 */

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 商品列表
     * @param session
     * @return
     */
    @GetMapping("/carts")
    public ResponseVo<CartVo> listCart(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return  cartService.list(user.getId());
    }

    /**
     * 添加商品
     * @param cartAddForm
     * @param session
     * @return
     */
    @PostMapping("/carts")
    public ResponseVo<CartVo> addCart(@Valid @RequestBody CartAddForm cartAddForm,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(),cartAddForm);
    }

    /**
     * 更新商品属性
     * @param cartUpdateForm
     * @param productId
     * @param session
     * @return
     */
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> updateCart(@RequestBody CartUpdateForm cartUpdateForm,
                                         @PathVariable Integer productId,
                                         HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.update(user.getId(),productId,cartUpdateForm);
    }

    /**
     * 删除商品
     * @param productId
     * @param session
     * @return
     */
    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> deleteCart(@PathVariable Integer productId,HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.delete(user.getId(),productId);
    }

    /**
     * 商品全选
     * @param session
     * @return
     */
    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    /**
     * 商品全不选
     * @param session
     * @return
     */
    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    /**
     * 商品总数量 用户显示在购物车处
     * @param session
     * @return
     */
    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.cartSum(user.getId());
    }

}
