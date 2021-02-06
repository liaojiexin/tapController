package com.vcmy.service.system.impl;

import com.vcmy.common.serivce.TokenService;
import com.vcmy.common.serivce.UserConstant;
import com.vcmy.dao.*;
import com.vcmy.entity.Role;
import com.vcmy.entity.User;
import com.vcmy.service.system.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 * @ClassName: UserServiceImpl.java
 * @Description: 用户信息 服务层
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:13:39
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private RoleDao roleDao;

	@Override
	public String login(User user) {
		User select = userDao.selectUserByName(user.getUserName());
		String errMsg = StringUtils.EMPTY;
		if (select == null) {
			errMsg = "用户名或密码错误";
		} else if (0 != select.getStatus()) {
			errMsg = "该账号已被冻结，请联系管理员";
		} else if (!select.getPassword().equals(user.getPassword())) {
			errMsg = "用户名或密码错误";
		} else {
			user.setUserId(select.getUserId());
			user.setRoleId(select.getRoleId());
		}
		return errMsg;
	}

	@Override
	public void updateLoginTime(Date date,String userName) {
		User user=new User();
		user.setLoginTime(date);
		user.setUserName(userName);
		userDao.updateByUserName(user);
	}

	@Override
	public int unique(String userName) {
		return userDao.checkUserNameUnique(userName);
	}

	@Override
	public int add(User user) {
		return userDao.insertSelective(user);
	}

	@Override
	public int delete(Integer userId) {
		return userDao.deleteUserById(userId);
	}

	@Override
	public int update(User user) {
		return userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<User> selectList(User user) {
		List<User> list = userDao.selectUserList(user);
		for (User user1:list){
			Role role =roleDao.selectByPrimaryKey(user1.getRoleId().intValue());
			user1.setRoleName(role.getRoleName());
		}
		return list;
	}

	@Override
	public int stop(Integer userId) {
		User user = new User();
		user.setUserId(userId);
		user.setStatus(UserConstant.STATUS_STOP);
		return userDao.updateStatus(user);
	}

	@Override
	public int start(Integer userId) {
		User user = new User();
		user.setUserId(userId);
		user.setStatus(UserConstant.STATUS_START);
		return userDao.updateStatus(user);
	}

	@Override
	public String selectSessionByUserId(Integer userid) {
		String sessionid =userDao.selectSessionByUserId(userid);
		if (sessionid==null||sessionid==""||sessionid.isEmpty())
			return null;
		else
			return sessionid;
	}

	@Override
	public int selectByRoleId(Integer roleId) {
		int sum=userDao.selectByRoleId(roleId);
		return sum;
	}

	@Override
	public User selectByUserId(Integer userId) {
		if (userId!=null)
			return userDao.selectByPrimaryKey(userId);
		else
			return null;
	}

	/**
	 * @Author: Galen
	 * @Description: 实现了UserDetailsService接口中的loadUserByUsername方法
	 * 执行登录,构建Authentication对象必须的信息,
	 * 如果用户不存在，则抛出UsernameNotFoundException异常
	 * @Date: 2019/3/27-16:04
	 * @Param: [s]
	 * @return: org.springframework.security.core.userdetails.UserDetails
	 **/
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		/**
		 * @Author: Galen
		 * @Description: 查询数据库，获取登录的用户信息
		 **/
		User user = userDao.selectUserByName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不对");
		}
		if (user.getStatus()==1){
			throw new DisabledException("账户被禁用");
		}
		String token = tokenService.createToken(user);
		user.setToken(token);
		return user;
	}
}
