package com.devil.mall.service.impl;

import com.devil.mall.dao.UserMapper;
import com.devil.mall.enums.ResponseEnum;
import com.devil.mall.pojo.User;
import com.devil.mall.service.IUserService;
import com.devil.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Hanyanjiao
 * @date 2020/5/15
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {

        //username 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());

        if(countByUsername > 0){
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }

        //email 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());

        if(countByEmail  > 0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        //密码加密--> MD5  //摘要
        user.setPassword( DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        //将数据写入数据库
        int resultCount = userMapper.insertSelective(user);

        if(resultCount == 0 ){
            return ResponseVo.error( ResponseEnum.ERROR);
        }

        user.setPassword("");

        return ResponseVo.success(user);

    }

    @Override
    public ResponseVo<User> login(String username,String password) {
        User user = userMapper.selectByUsername(username);

        if(user==null){
//            用户或密码不存在
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if(!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");
        return ResponseVo.success(user);
    }

}
