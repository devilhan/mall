package com.devil.mall.service.impl;

import com.devil.mall.dao.ProductMapper;
import com.devil.mall.enums.ProductStatusEnum;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.pojo.Product;
import com.devil.mall.service.ICategoryService;
import com.devil.mall.service.IProductService;
import com.devil.mall.vo.ProductDetailVo;
import com.devil.mall.vo.ProductVo;
import com.devil.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hanyanjiao
 * @date 2020/5/25
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();

       if(categoryId != null){
           categoryService.findSubCategoryId(categoryId,categoryIdSet);

           categoryIdSet.add(categoryId);
       }

        PageHelper.startPage(pageNum,pageSize);

//        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet.size() == 0 ? null :categoryIdSet);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);

        List<ProductVo> productVos = products.stream().map(e -> {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(e, productVo);
            return productVo;
        }).collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productVos);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        if(product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode()) ||
                product.getStatus().equals(ProductStatusEnum.DELETE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();

        BeanUtils.copyProperties(product,productDetailVo);

        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
