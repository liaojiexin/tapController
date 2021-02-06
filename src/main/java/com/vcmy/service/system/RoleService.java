package com.vcmy.service.system;

import com.vcmy.entity.Role;

import java.util.List;

/**
 * @ClassName: RoleService.java
 * @Description: 该类的功能描述
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月17日 上午10:13:02
 */
public interface RoleService {

	 Integer add(Role role);

	 Integer delete(Integer roleId);

	 Integer update(Role role);

	 List<Role> selectList(Role role);

}
