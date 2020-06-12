package com.devil.mall.service.impl;

import com.devil.mall.MallApplicationTests;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.form.ShippingForm;
import com.devil.mall.service.IShippingService;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class ShippingServiceImplTest extends MallApplicationTests {

    @Autowired
    private IShippingService shippingService;

    private Integer uid = 1;

    private ShippingForm shippingForm;

    @Before
    public void before(){
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverName("韩小妖");
        shippingForm.setReceiverAddress("犄角旮旯");
        shippingForm.setReceiverCity("北京市");
        shippingForm.setReceiverDistrict("朝阳区");
        shippingForm.setReceiverMobile("13588782424");
        shippingForm.setReceiverPhone("13606630000");
        shippingForm.setReceiverProvince("北京市");
        shippingForm.setReceiverZip("100000");
        this.shippingForm = shippingForm;
    }
    @Test
    public void add() {
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, shippingForm);

        log.info("response is {}",responseVo.getData());
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }


    @Test
    public void delete() {
        ResponseVo responseVo = shippingService.delete(2, 9);
        log.info("response is {}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update() {
        shippingForm.setReceiverPhone("13588782727");
        ResponseVo responseVo = shippingService.update(uid, 9, shippingForm);
        log.info("responseVo is {}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void list() {

        ResponseVo<PageInfo> responseVo = shippingService.list(uid, 1, 10);
        log.info("responseVo is {}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}