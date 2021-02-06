package com.vcmy.controller.system;

import com.vcmy.annotation.Log;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import com.vcmy.service.system.*;

/**
 * @ClassName: RoleController
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/13 20:19
 */
@RestController
@Api(tags = "系统-角色相关接口")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    /**
     * @Author liaojiexin
     * @Description 查找所有角色
     * @Date 2020/12/13 22:24
     * @Param [role, request]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("查找所有角色")
    @RequestMapping(value = "/tapapi/system/role/get", method = RequestMethod.GET)
    public Message findList(Role role, HttpServletRequest request) {
        setPageInfo(request, role);
        List<Role> list = roleService.selectList(role);
        return getDataMessage(list);
    }

    /**
     * @Author liaojiexin
     * @Description 添加新角色
     * @Date 2020/12/14 10:27
     * @Param [role]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("添加新角色")
    @Log(module = "角色管理",action = "添加新角色")
    @RequestMapping(value = "/tapapi/system/role/add", method = RequestMethod.POST)
    public Message add(@RequestBody Role role) {
        if (roleService.add(role) == 1) {
            return Message.ok();
        }
        return Message.error("添加失败,角色名称已存在");
    }

    /**
     * @Author liaojiexin
     * @Description 删除角色
     * @Date 2020/12/14 10:42
     * @Param [roleId]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("删除角色")
    @Log(module = "角色管理",action = "删除角色")
    @ApiImplicitParam(value = "角色id",name = "roleId")
    @RequestMapping(value = "/tapapi/system/role/delete/{roleId}", method = RequestMethod.DELETE)
    public Message delete(@PathVariable("roleId") Integer roleId) {
        if (roleId == null) {
            return Message.error("角色不存在");
        }
        if(userService.selectByRoleId(roleId)>0){
            return Message.error("该角色下还有账号存在，请先删除相关账号后再尝试");
        }
        if (roleService.delete(roleId) == 1) {
            return Message.ok();
        }
        return Message.error("角色不存在");
    }

    /**
     * @Author liaojiexin
     * @Description 修改角色信息
     * @Date 2020/12/14 11:03
     * @Param [role]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("修改角色")
    @Log(module = "角色管理",action = "修改角色信息")
    @RequestMapping(value = "/tapapi/system/role/update", method = RequestMethod.PUT)
    public Message update(@RequestBody Role role) {
        if (roleService.update(role) == 1) {
            return Message.ok();
        }
        return Message.error();
    }

}
