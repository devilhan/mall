package com.devil.mall.controller;

import com.devil.mall.consts.MallConst;
import com.devil.mall.enums.RoleEnum;
import com.devil.mall.form.UserLoginForm;
import com.devil.mall.form.UserRegisterForm;
import com.devil.mall.pojo.User;
import com.devil.mall.service.IUserService;
import com.devil.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author Hanyanjiao
 * @date 2020/5/15
 */

@Slf4j
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户注册
     * @param userForm
     * @return
     */
    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userForm){

      log.info("username is {}",userForm.getUsername());
        User user = new User();
        BeanUtils.copyProperties(userForm,user);
        user.setRole(RoleEnum.CUSTOMER.getCode());
        return userService.register(user);
    }


    /**
     * 用户登录
     * @param userLoginForm
     * @param session
     * @return
     */
    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  HttpSession session){

        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //设置session  session保存在内存里 ,改进版：token + redis
        //HttpSession session = httpServletRequest.getSession();

        session.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());
        return userResponseVo;
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        //如果数据不是最新的，可以重新进行查询
        return ResponseVo.success(user);
    }

    /**
     * 退出登录
     * {@link org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory}
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }

}
