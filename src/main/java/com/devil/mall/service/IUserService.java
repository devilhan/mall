package com.devil.mall.service;

import com.devil.mall.pojo.User;
import com.devil.mall.vo.ResponseVo;

/**
 * @author Hanyanjiao
 * @date 2020/5/15
 */

public interface IUserService {

    //注册

    ResponseVo<User> register(User user);

    //登录
    ResponseVo<User> login(String username,String password);



}
