package com.devil.mall.service.impl;

import com.devil.mall.MallApplicationTests;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.service.IOrderService;
import com.devil.mall.vo.OrderVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class OrderServiceImplTest extends MallApplicationTests {

    @Autowired
    private IOrderService orderService;

    private Integer uid = 2;

    private Integer shippingId = 8; // 9--10

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid,shippingId);
        log.info("responseVo is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void list(){
        ResponseVo<PageInfo> responseVo = orderService.list(uid,2,5);
        log.info("responseVo is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void detail(){
        ResponseVo<OrderVo> responseVo = orderService.detail(uid,1590652281641L);
        log.info("responseVo is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void cancel(){
        ResponseVo responseVo = orderService.cancel(uid,1590652281641L);
        log.info("responseVo is {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

}