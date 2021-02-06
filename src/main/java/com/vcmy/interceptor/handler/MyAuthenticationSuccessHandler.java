package com.vcmy.interceptor.handler;

import com.vcmy.common.serivce.TokenService;
import com.vcmy.entity.Message;
import com.vcmy.util.SecurityUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:17
 * @Description: 认证成功的处理
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Map map =new HashMap();
//        map.put("msg","登录成功");
        map.put("User",SecurityUserUtil.getCurrentUser());
        Message message = Message.ok(map);
        new GalenWebMvcWrite().writeToWeb(response, message);
        System.out.println("登录成功!");
    }
}