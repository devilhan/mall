package com.devil.mall.service;

import com.devil.mall.form.CartAddForm;
import com.devil.mall.form.CartUpdateForm;
import com.devil.mall.pojo.Cart;
import com.devil.mall.vo.CartVo;
import com.devil.mall.vo.ResponseVo;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/5/26
 */
public interface ICartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm addForm);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> delete(Integer uid,Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> cartSum(Integer uid);

    List<Cart> listForCart(Integer uid);
}
