package com.devil.mall.service;

import com.devil.mall.vo.CategoryVo;
import com.devil.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * @author Hanyanjiao
 * @date 2020/5/22
 */

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> findAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
