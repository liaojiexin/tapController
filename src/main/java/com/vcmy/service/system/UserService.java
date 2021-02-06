package com.vcmy.service.system;

import com.vcmy.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserService.java
 * @Description: 用户信息 服务层
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:16:23
 */
public interface UserService {

	 String login(User user);

	 int unique(String userName);

	 int add(User user);

	 int update(User user);

	 int delete(Integer userId);

	 List<User> selectList(User user);

	 int stop(Integer userId);

	 int start(Integer userId);

    String selectSessionByUserId(Integer userid);

	void updateLoginTime(Date date,String userName);

	int selectByRoleId(Integer roleId);

	User selectByUserId(Integer id);

	UserDetails loadUserByUsername(String userName);
}
