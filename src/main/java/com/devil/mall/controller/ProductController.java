package com.devil.mall.controller;

import com.devil.mall.service.IProductService;
import com.devil.mall.vo.ProductDetailVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hanyanjiao
 * @date 2020/5/25
 */

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * 类目下商品列表
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/products")
    public ResponseVo<PageInfo> findProducts(@RequestParam(required = false) Integer categoryId,
                                             @RequestParam(required = false,defaultValue = "1") Integer pageNum ,
                                             @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        return productService.list(categoryId,pageNum,pageSize);
    }

    /**
     * 商品信息
     * @param productId
     * @return
     */
    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> findDetail(@PathVariable Integer productId){
        return productService.detail(productId);
    }
}
