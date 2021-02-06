package com.vcmy.interceptor.handler;

import com.vcmy.entity.Message;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:21
 * @Description: 注销登录处理
 **/

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Message message = Message.ok("注销成功!");
        new GalenWebMvcWrite().writeToWeb(response, message);
        System.out.println("注销成功!");
    }
}
