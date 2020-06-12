package com.devil.mall.controller;

import com.devil.mall.service.ICategoryService;
import com.devil.mall.vo.CategoryVo;
import com.devil.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/5/22
 */

@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 获取全部商品类目
     * @return
     */
    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> findAll(){
        return categoryService.findAll();
    }
}
