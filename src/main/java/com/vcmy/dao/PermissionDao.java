package com.vcmy.dao;

import com.vcmy.entity.Permission;
import com.vcmy.entity.RolePermission;

import java.util.List;

public interface PermissionDao {
    int deleteByPrimaryKey(Integer permissionid);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionid);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectAllPermission(Permission permission);

    List<Permission> selectPermissionByRoleId(Integer roleId);

    List<RolePermission> getAllPermission();
}