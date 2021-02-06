package com.vcmy.service.system.impl;

import com.vcmy.dao.*;
import com.vcmy.entity.*;
import com.vcmy.service.system.*;
import com.vcmy.util.WebContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

	@Override
	@Transactional
	public Integer add(Role role) {
		role.setCreateBy(WebContextUtils.getName());
		Integer count=roleDao.selectRoleName(role.getRoleName());
		if (count>0){
		    return 0;
        }else {
            Integer result = roleDao.insertSelective(role);
            return result;
        }
	}

	@Override
	@Transactional
	public Integer delete(Integer roleId) {
        rolePermissionDao.deleteRolePermissionByRoleId(roleId);
		Integer count = roleDao.deleteByPrimaryKey(roleId);
		return count;
	}

	@Override
	@Transactional
	public Integer update(Role role) {
		if (null == roleDao.selectByPrimaryKey(role.getRoleId())) {
			return 0;
		}
		return roleDao.updateByPrimaryKeySelective(role);
	}


	@Override
	public List<Role> selectList(Role role) {
		List<Role> list = roleDao.selectRoleList(role);
		if (list != null && list.size() > 0) {
		    return list;
		}else
		    return null;
	}

}
