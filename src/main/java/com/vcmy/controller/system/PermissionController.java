package com.vcmy.controller.system;

import com.vcmy.annotation.Log;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.Message;
import com.vcmy.entity.Permission;
import com.vcmy.service.system.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: PermissionController
 * @Description: TODO  权限
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/14 18:01
 */
@RestController
@Api(tags = "系统-权限相关接口")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    /**
     * @Author liaojiexin
     * @Description 查出所有权限
     * @Date 2020/12/15 9:35
     * @Param [permission, request]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("查出所有权限")
    @RequestMapping(value = "/tapapi/system/permission/select", method = RequestMethod.GET)
    public Message selectAllPermission(Permission permission, HttpServletRequest request){
        List<Permission> list=permissionService.selectAllPermission(permission);
        return getDataMessage(list);
    }

    /**
     * @Author liaojiexin
     * @Description 查出当前角色有哪些角色
     * @Date 2020/12/15 10:01
     * @Param
     * @return
     **/
    @ApiOperation("查出当前角色有哪些权限")
    @ApiImplicitParam(value = "角色id",name = "roleId")
    @RequestMapping(value = "/tapapi/system/permission/selectbyrole",method = RequestMethod.GET)
    public Message selectPermissionByRoleId(Integer roleId){
        List<Permission> list=permissionService.selectPermissionByRoleId(roleId);
        return getDataMessage(list);
    }

    /**
     * @Author liaojiexin
     * @Description 修改角色权限
     * @Date 2020/12/15 13:43
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("修改角色权限")
    @Log(module = "角色管理",action = "修改角色权限")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "角色Id",name = "roleId"),
        @ApiImplicitParam(value = "权限id集合",name = "permissionIdList")
    })
    @RequestMapping(value = "/tapapi/system/permission/update/{roleId}",method = RequestMethod.PUT)
    public Message updateRolePermission(@RequestBody List<Integer> permissionIdList,@PathVariable("roleId") Integer roleId){
        permissionService.updateRolePermission(permissionIdList,roleId);
        return Message.ok();
    }
}
