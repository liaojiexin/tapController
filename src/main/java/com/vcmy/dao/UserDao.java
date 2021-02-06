package com.vcmy.dao;

import java.util.List;

import com.vcmy.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @ClassName: UserDao.java
 * @Description:  用户表 数据层
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:01:31
 */
public interface UserDao {

	/**
	 * 通过用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @return 用户对象信息
	 */
	User selectUserByName(String userName);

	/**
	 * 通过用户ID删除用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return 结果
	 */
	int deleteUserById(Integer userId);

	/**
	 * 插入新用户
	 * @param user
	 * @return
	 */
	int insertSelective(User user);

	/**
	 * 校验用户名称是否唯一
	 * 
	 * @param loginName
	 *            登录名称
	 * @return 结果
	 */
	int checkUserNameUnique(@Param("loginName") String loginName);

	/**
	 * @Title: selectUserList
	 * @return List<User>    返回类型
	 */
	List<User> selectUserList(User user);

	void updateByUserName(User user);

	int updateStatus(User user);

    String selectSessionByUserId(@Param("userId") Integer userId);

	int updateByPrimaryKeySelective(User user);

	int selectByRoleId(@Param("roleId") Integer roleId);

	User selectByPrimaryKey(@Param("userId") Integer userId);
}
