package com.devil.mall.service.impl;

import com.devil.mall.MallApplicationTests;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.form.CartAddForm;
import com.devil.mall.form.CartUpdateForm;
import com.devil.mall.service.ICartService;
import com.devil.mall.vo.CartVo;
import com.devil.mall.vo.ResponseVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CartServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Integer productId = 28;

    private Integer uid = 2;

    @Test
    public void add() {
        log.info("新增购物车");
        CartAddForm cartAddForm = new CartAddForm(productId,false);
        ResponseVo<CartVo> cartVo = cartService.add(uid, cartAddForm);
        log.info("list={}",gson.toJson(cartVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),cartVo.getStatus());
    }

    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(uid);
        log.info("list={}",gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());
    }

    @Test
    public void update(){
        CartUpdateForm updateForm = new CartUpdateForm(5,false);
        ResponseVo<CartVo> responseVo = cartService.update(uid,productId,updateForm);
        log.info("list is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void delete(){
        log.info("删除购物车");
        ResponseVo<CartVo> responseVo = cartService.delete(uid,productId);
        log.info("list is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void selectAll(){
        ResponseVo<CartVo> responseVo = cartService.selectAll(uid);
        log.info("result is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void unSelectAll(){
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(uid);
        log.info("result is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void sum(){
        ResponseVo<Integer> responseVo = cartService.cartSum(uid);
        log.info("result is {}",responseVo.getData());
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

}