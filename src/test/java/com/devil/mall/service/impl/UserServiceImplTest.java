package com.devil.mall.service.impl;

import com.devil.mall.MallApplicationTests;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.enums.RoleEnum;
import com.devil.mall.pojo.User;
import com.devil.mall.service.IUserService;
import com.devil.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


//@Transactional
public class UserServiceImplTest extends MallApplicationTests {

    @Autowired
    private IUserService userService;

    public static final String USERNAME ="devil";

    public static final String PASSWORD="123456";
    @Test
    public void register() {
        User user = new User(USERNAME,PASSWORD,"hanyanjiao_hyj@163.com",RoleEnum.ADMIN.getCode());
        userService.register(user);
    }

    @Test
    public void login() {
        ResponseVo<User> responseVo = userService.login(USERNAME,PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}