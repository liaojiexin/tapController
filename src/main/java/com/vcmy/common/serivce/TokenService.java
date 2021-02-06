package com.vcmy.common.serivce;

import com.vcmy.entity.User;

/**
 *
 * @ClassName: TokenService.java
 * @Description:  token管理 服务层
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:15:55
 */
public interface TokenService {

    String createToken(User user);

    boolean checkToken(String token);

    void deleteToken(String token);

    void clearToken(String username);

}