package com.vcmy.interceptor.handler;

import com.vcmy.common.serivce.TokenService;
import com.vcmy.service.system.*;
import com.vcmy.util.WebContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SessionIdInterceptor
 * @Author: liaojiexin
 * @Description: 功能描述
 * @Date: 2020/8/2 21:49
 * @Modified By:
 * @Version: 1.0
 */
@Component
public class SessionIdInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
          if(StringUtils.isNotBlank(WebContextUtils.getToken()) ){
            Integer userid=WebContextUtils.getId();
            String token1 = userService.selectSessionByUserId(userid);
            if (WebContextUtils.getToken().equals(token1)) {
                return true;
            } else {
                String token = WebContextUtils.getToken();
                tokenService.deleteToken(token);
                return false;
            }
        }else{
            String token = WebContextUtils.getToken();
            tokenService.deleteToken(token);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
