package com.vcmy.service.system.impl;

import com.vcmy.dao.PermissionDao;
import com.vcmy.dao.RoleDao;
import com.vcmy.dao.RolePermissionDao;
import com.vcmy.entity.Permission;
import com.vcmy.entity.Role;
import com.vcmy.entity.RolePermission;
import com.vcmy.service.system.PermissionService;
import com.vcmy.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: PermissionServiceImpl
 * @Description: TODO   权限
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/14 18:03
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Permission> selectAllPermission(Permission permission) {
        List<Permission> list=permissionDao.selectAllPermission(permission);
        List<Permission> permissionList= TreeUtils.assembleTreePermission(list);
        return permissionList;
    }

    @Override
    public List<Permission> selectPermissionByRoleId(Integer roleId) {
        List<Permission> list=permissionDao.selectPermissionByRoleId(roleId);
        return list;
    }

    @Override
    public int updateRolePermission(List permissionIdList, Integer roleId) {
        RolePermission rolePermission=new RolePermission();
        //先删除当前角色所有权限，再把传递过来修改的权限加进去
        rolePermissionDao.deleteRolePermissionByRoleId(roleId);
        for(int i=0;i<permissionIdList.size();i++){
            rolePermission.setPermissionId((Integer) permissionIdList.get(i));
            rolePermission.setRoleId(roleId);
            rolePermissionDao.insert(rolePermission);
        }
        Role role=new Role();
        role.setRoleId(roleId);
        roleDao.updateByPrimaryKeySelective(role);
        return 1;
    }
}
