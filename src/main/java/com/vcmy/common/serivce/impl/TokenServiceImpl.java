package com.vcmy.common.serivce.impl;


import com.vcmy.entity.User;
import com.vcmy.common.serivce.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @ClassName: TokenServiceImpl.java
 * @Description:  token服务 服务层处理
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:12:53
 */
@Service
public class TokenServiceImpl implements TokenService{
	private static Map<String, User> tokenMap = new ConcurrentHashMap<>();
	

	
	@Override
	public String createToken(User user) {
		clearToken(user.getUserName());
		String token = UUID.randomUUID().toString();	
		tokenMap.put(token, user);
		return token;
	}

	@Override
	public boolean checkToken(String token) {
		return !StringUtils.isEmpty(token) && tokenMap.containsKey(token);
	}

	@Override
	public void deleteToken(String token) {
		if(StringUtils.isBlank(token)){
			return;
		}
		tokenMap.remove(token);
	}
	
	public void clearToken(String username){
		if(StringUtils.isBlank(username)){
			return;
		}
		for(Map.Entry<String, User> tokenEntity:tokenMap.entrySet()){
			if(username.equals(tokenEntity.getValue().getUserName())){
				deleteToken(tokenEntity.getKey());
				break;
			}
		}
	}

	public static Map<String, User> getTokenMap(){
		return tokenMap;
	}

}
