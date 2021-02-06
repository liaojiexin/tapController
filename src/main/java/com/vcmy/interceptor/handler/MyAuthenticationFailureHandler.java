package com.vcmy.interceptor.handler;

import com.vcmy.entity.Message;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author: Galen
 * @Date: 2019/3/28-9:17
 * @Description: 认证失败的处理
 **/

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Message message;
        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            message = Message.error("账户名或者密码输入错误!");
        } else if (exception instanceof LockedException) {
            message = Message.error("账户被锁定，请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            message = Message.error("密码过期，请联系管理员!");
        } else if (exception instanceof AccountExpiredException) {
            message = Message.error("账户过期，请联系管理员!");
        } else if (exception instanceof DisabledException) {
            message = Message.error("账户被禁用，请联系管理员!");
        } else {
            message = Message.error("登录失败!");
        }
//        response.setStatus(401);
        response.setStatus(200);
        new GalenWebMvcWrite().writeToWeb(response, message);
    }
}
