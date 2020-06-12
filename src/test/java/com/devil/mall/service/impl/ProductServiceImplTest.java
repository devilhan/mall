package com.devil.mall.service.impl;

import com.devil.mall.MallApplicationTests;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.service.IProductService;
import com.devil.mall.vo.ProductDetailVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImplTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void findProductList() {
        ResponseVo<PageInfo> responseVo = productService.list(100002, 1, 2);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void detail() {
        ResponseVo<ProductDetailVo> productDetailVoResponseVo = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),productDetailVoResponseVo.getStatus());
    }
}