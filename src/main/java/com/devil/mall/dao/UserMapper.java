package com.devil.mall.dao;

import com.devil.mall.pojo.User;

/**
* Created by ESC han on 2020/05/15
*/
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countByUsername(String username);

    int countByEmail(String email);

    User selectByUsername(String username);
}