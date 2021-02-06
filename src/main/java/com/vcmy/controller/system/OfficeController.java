package com.vcmy.controller.system;

import com.vcmy.controller.BaseController;
import com.vcmy.entity.*;
import com.vcmy.service.system.*;
import com.vcmy.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 组织机构,部门
 * 
 * @author yh
 * @date 2020-04-15 15:55:25
 */
@Api(tags = "部门相关接口(暂时不用)")
@RestController
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UserService userService;

	/**
	 * @Author liaojiexin
	 * @Description 查看部门
	 * @Date 2020/12/14 15:49
	 * @Param [office, request]
	 * @return com.vcmy.entity.Message
	 **/
/*	@ApiImplicitParam("查看查询部门")
	@RequestMapping(value = "/tapapi/system/office/tree", method = RequestMethod.GET)
	public Message findTree(Office office) {
		List<Office> list = officeService.selectOfficeTreeList(office);
		return responseMessage(list.size(),PageUtils.startPage(list, office.getPage(), office.getCount()) );
	}*/

}
