package com.devil.mall.interceptor;

import com.devil.mall.consts.MallConst;
import com.devil.mall.exception.UserLoginException;
import com.devil.mall.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hanyanjiao
 * @date 2020/5/22
 */

public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * true表示继续流程，false表示中断
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);

        if(user == null ){
            throw new UserLoginException();
        }
        return true;
    }
}
