package com.devil.mall.service.impl;

import com.devil.mall.MallApplicationTests;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.service.ICategoryService;
import com.devil.mall.vo.CategoryVo;
import com.devil.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CategoryServiceImplTest extends MallApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void findAll() {
        ResponseVo<List<CategoryVo>> listResponseVo = categoryService.findAll();

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),listResponseVo.getStatus());
    }

    @Test
    public void findSubCategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100001,set);
        log.info("set is {}",set);
    }
}