package com.vcmy.dao;

import com.vcmy.entity.RolePermission;

public interface RolePermissionDao {
    int deleteByPrimaryKey(Integer permissionid);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Integer permissionid);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    void deleteRolePermissionByRoleId(Integer roleId);
}