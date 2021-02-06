package com.vcmy.dao;

import com.vcmy.entity.Role;

import java.util.List;

public interface RoleDao {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleList(Role role);

    Integer selectRoleName(String roleName);

}