package com.vcmy.service.system;

import com.vcmy.entity.Permission;
import com.vcmy.entity.RolePermission;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author liaojiexin
 * @Description 权限
 * @Date 2020/12/14 18:06
 * @Param
 * @return
 **/
public interface PermissionService {
    List<Permission> selectAllPermission(Permission permission);

    List<Permission> selectPermissionByRoleId(Integer roleId);

    int updateRolePermission(List permissionIdList, Integer roleId);
}
