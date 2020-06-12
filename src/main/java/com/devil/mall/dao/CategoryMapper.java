package com.devil.mall.dao;

import com.devil.mall.pojo.Category;

import java.util.List;

/**
* Created by ESC han on 2020/05/22
*/
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> findAll();
}