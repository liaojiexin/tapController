package com.vcmy.util;

import com.vcmy.common.Constant;
import com.vcmy.entity.User;
import com.vcmy.exception.TokenException;
import com.vcmy.common.serivce.impl.TokenServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 
 * @ClassName: WebContextUtils.java
 * @Description: web上下文工具
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:09:05
 */
public class WebContextUtils {
	private WebContextUtils() {
	}

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getToken(HttpServletRequest request) {
		return request.getHeader(Constant.DEFAULT_TOKEN_NAME);
	}

	public static String getToken() {
		return getToken(getHttpServletRequest());
	}
	
	public static String getName(){
		String token = getToken();
		if(StringUtils.isBlank(token) || !getTokenMap().containsKey(token)){
			throw new TokenException("token已经失效");
		}
		return getTokenMap().get(token).getUserName();
	}
	public static Integer getId(){
		String token = getToken();
		if(StringUtils.isBlank(token) || !getTokenMap().containsKey(token)){
			throw new TokenException("token已经失效");
		}
		return getTokenMap().get(token).getUserId();
	}
	public static Map<String, User> getTokenMap(){
		return TokenServiceImpl.getTokenMap();
	}
	
	
}
