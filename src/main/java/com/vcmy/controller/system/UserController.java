package com.vcmy.controller.system;

import com.vcmy.annotation.IgnoreSecurity;
import com.vcmy.annotation.Log;
import com.vcmy.common.ParamConstant;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.Message;
import com.vcmy.entity.User;
import com.vcmy.entity.UserPassword;
import com.vcmy.service.system.*;
import com.vcmy.common.serivce.TokenService;
import com.vcmy.util.*;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liaojiexin
 * @Description 
 * @Date 2020/12/10 13:35
 * @Param 
 * @return 
 **/
@RestController
@Api(tags = "系统-用户相关接口")
public class UserController extends BaseController {

	@Autowired
	private PBKDF2Util pbkdf2Util;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * @Title: login
	 * @Description: 根据用户名密码登录
	 * @param user User
	 * @return Message 返回类型
	 */
	@IgnoreSecurity
	@ApiOperation("用户登录")
	@RequestMapping(value = "/tapapi/system/users/login", method = RequestMethod.POST)
	public Message login(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
		//限制用户错误输入5次
/*	    int useridNumber =userService.selectCountByUserName(user.getUserName());        //判断表中是否有该用户的记录
	    if(useridNumber<1){    //没有的话插入记录
	        userService.insertUserNumber(user.getUserName());
        }else{
	        Date time =userService.selectTimeByUserId(user.getUserName());
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            if(fmt.format(new Date()).equals(fmt.format(time))){   //如果登录时间是同一天,判断登录次数是否上限,5次
                int number =userService.selectNumberByUserId(user.getUserName());
                if(number>10)
                    return Message.error("密码连续输错10次，账号被冻结，请明天再尝试");
            }else {     //把次数清零，并把今天的时间修改上
                userService.updateUserNumber(user.getUserName());
            }
        }*/

		user.setPassword(pbkdf2Util.getEncryptedPassword(user.getPassword(),"64"));
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String msg = userService.login(user);
		HttpSession session = request.getSession();

		//验证码
//		String code = user.getCode().toLowerCase();
//		if (!code.equals(session.getAttribute("verCode"))) {
//			return Message.error("验证码不正确！");
//		}

		if (StringUtils.isBlank(msg)) {     //登录成功
            String token = tokenService.createToken(user);
		    //userService.updateUserNumber(user.getUserName());

		    //每次登录成功后，做一次用户日志数据库删除操作，把60天前的数据删除掉
			/*Calendar calendar =Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 60);
			Date date7day=calendar.getTime();
			userActionService.deleteUserAction7day(date7day);*/

		    //限制一个账户只能被一个人登录
		    //用户登录以后，根据判断去维护member_sessionid表
/*		    String tokenid =userService.selectSessionByUserId(user.getUserId());
            if(tokenid==null){
                userService.insertSession(user.getUserId(),token);
            }else{
                userService.updateSession(user.getUserId(),token);
            }*/

			//修改个人信息里面的最后登录时间
			Date date=new Date();
			userService.updateLoginTime(date,user.getUserName());

			session.setAttribute("user", user);

			Map<String,Object> responce=new HashMap();
			responce.put("token",token);
			responce.put("roleId",user.getRoleId());

			Message message = Message.ok(responce);
			return message;
		}
//		userService.updateUserNumberAdd(user.getUserName());
		return Message.error(msg);
	}

	/**
	 * @Author liaojiexin
	 * @Description 退出登录
	 * @Date 2020/12/11 11:41
	 * @Param []
	 * @return void
	 **/
	@ApiOperation("退出登录")
	@RequestMapping(value = "/tapapi/system/users/logout", method = RequestMethod.GET)
	public Message logout() {
		String token = WebContextUtils.getToken();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute(ParamConstant.COMPANY_NAME); // 清除session
		session.removeAttribute(ParamConstant.LOGO_URL); // 清除session
		session.removeAttribute(ParamConstant.MENUS); // 清除session
		session.removeAttribute("user");
		tokenService.deleteToken(token);
		return Message.ok();
	}

	/**
	 * @Title: unique
	 * @Description: 查询账号是否存在
	 * @param userName 用户账号
	 * @return Message 返回类型
	 */
	@ApiOperation("检查账号是否已经存在")
	@ApiImplicitParam(name = "userName",value = "账号")
	@RequestMapping(value = "/tapapi/system/users/{userName}/unique", method = RequestMethod.GET)
	public Message unique(@PathVariable("userName") String userName) {
		if (userService.unique(userName) == 0) {
			return Message.ok();
		}
		return Message.error("账号已存在");
	}

	/**
	 * @Author liaojiexin
	 * @Description 添加新用户
	 * @Date 2020/12/11 14:32
	 * @Param [user]
	 * @return com.vcmy.entity.Message
	 **/
	@ApiOperation("添加新用户")
	@Log(module = "账号管理",action = "添加新用户")
	@RequestMapping(value = "/tapapi/system/users/add", method = RequestMethod.POST)
	public Message add(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
//		user.setPassword(pbkdf2Util.getEncryptedPassword(user.getPassword(),"64"));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));		//bcrypt加密
		if (userService.unique(user.getUserName()) == 0 ) {
			if(userService.add(user) > 0){
				return Message.ok();
			}else {
				return Message.error("添加失败，请检查输入信息");
			}
		}else
			return Message.error("添加失败，账号已存在");
	}

	/**
	 * @Author liaojiexin
	 * @Description 删除用户
	 * @Date 2020/12/11 14:54
	 * @Param [userId]
	 * @return com.vcmy.entity.Message
	 **/
	@ApiOperation("删除用户")
	@Log(module = "账号管理",action = "删除用户")
	@ApiImplicitParam(name = "userId",value = "用户id")
	@RequestMapping(value = "/tapapi/system/users/delete/{userId}", method = RequestMethod.DELETE)
	public Message delete(@PathVariable("userId") Integer userId) {
		if (userService.delete(userId) > 0) {
			return Message.ok();
		}
		return Message.error("用户不存在");
	}

	/**
	 * @Author liaojiexin
	 * @Description 禁用账号
	 * @Date 2020/12/11 15:00
	 * @Param [userId]
	 * @return com.vcmy.entity.Message
	 **/
	@ApiOperation("禁用账号")
	@Log(module = "账号管理",action = "禁用账户")
	@ApiImplicitParam(name = "userId",value = "用户id")
	@RequestMapping(value = "/tapapi/system/users/stop/{userId}", method = RequestMethod.PUT)
	public Message stop(@PathVariable("userId") Integer userId) {
		if (userService.stop(userId) == 1) {
			return Message.ok();
		}
		return Message.error("账户不存在");
	}

	/**
	 * @Author liaojiexin
	 * @Description 启用账号
	 * @Date 2020/12/11 15:07
	 * @Param [userId]
	 * @return com.vcmy.entity.Message
	 **/
	@ApiOperation("启用账号")
	@Log(module = "账号管理",action = "启用账号")
	@ApiImplicitParam(name = "userId",value = "用户id")
	@RequestMapping(value = "/tapapi/system/users/start/{userId}", method = RequestMethod.PUT)
	public Message start(@PathVariable("userId") Integer userId) {
		if (userService.start(userId) == 1) {
			return Message.ok();
		}
		return Message.error("账号不存在");
	}

	/**
	 * @Author liaojiexin
	 * @Description 查询所有用户
	 * @Date 2020/12/11 15:49
	 * @Param [user, request]
	 * @return com.vcmy.entity.Message
	 **/
	@ApiOperation("查询所有用户")
	@RequestMapping(value = "/tapapi/system/users/get", method = RequestMethod.GET)
	public Message query(User user, HttpServletRequest request) {
		setPageInfo(request, user); // 对http请求结果进行分页
		List<User> list = userService.selectList(user);
		return getDataMessage(list);
	}

	/**
	 * @Author liaojiexin
	 * @Description 修改个人信息
	 * @Date 2020/12/13 19:53
	 * @Param [user]
	 * @return com.vcmy.entity.Message
	 **/
	@ApiOperation("修改个人信息")
	@Log(module = "账号管理",action = "修改个人信息")
	@RequestMapping(value = "/tapapi/system/users/update", method = RequestMethod.PUT)
	public Message update(@RequestBody User user) throws InvalidKeySpecException, NoSuchAlgorithmException{
//		user.setPassword(pbkdf2Util.getEncryptedPassword(user.getPassword(),"64"));
//		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (StringUtils.isNotBlank(user.getPassword())) {
			tokenService.clearToken(user.getUserName());
		}
		if (userService.update(user) > 0) {
			return Message.ok();
		}
		return Message.error();
	}

	/**
	 * @Author liaojiexin
	 * @Description 修改密码
	 * @Date 2021/2/1 14:19
	 * @Param
	 * @return
	 **/
	@ApiOperation("修改个人密码")
	@Log(module = "账号管理",action = "修改密码")
	@RequestMapping(value = "/tapapi/system/users/updatePassword", method = RequestMethod.PUT)
	public Message updatePassword(@RequestBody UserPassword userPassword){
		User user=userService.selectByUserId(userPassword.getUserId());
		if(user!=null && StringUtils.isNotBlank(userPassword.getNewPassword())
				&& StringUtils.isNotBlank(userPassword.getOldPassword())){
			boolean f = bCryptPasswordEncoder.matches(userPassword.getOldPassword(),user.getPassword());	//解密
			if (f){
				tokenService.clearToken(user.getUserName());
				user.setPassword(bCryptPasswordEncoder.encode(userPassword.getNewPassword()));	//加密
				if (userService.update(user) > 0) {
					return Message.ok();
				}
			}else {
				return Message.error("修改失败，原密码错误");
			}
		}
		return Message.error();
	}

}
