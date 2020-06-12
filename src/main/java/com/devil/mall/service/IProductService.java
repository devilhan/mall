package com.devil.mall.service;

import com.devil.mall.vo.ProductDetailVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

/**
 * @author Hanyanjiao
 * @date 2020/5/25
 */
public interface IProductService {
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> detail(Integer productId);
}
