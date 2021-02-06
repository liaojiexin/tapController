package com.vcmy.entity;

import com.vcmy.util.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author liaojiexin
 * @Description 
 * @Date 2020/12/10 11:34
 * @Param 
 * @return 
 **/
@ApiModel("用户")
public class User extends PageDomain implements UserDetails {
	@ApiModelProperty(value = "用户id")
	private Integer userId; // 用户Id
	@ApiModelProperty(value = "账号")
	private String userName; // 账号
	@ApiModelProperty(value = "密码")
	private String password; // 密码
	@ApiModelProperty(value = "真实姓名")
	private String name; // 真实姓名
	@ApiModelProperty(value = "联系电话")
	private String userTel; // 联系电话
/*	@ApiModelProperty(value = "部门id")
	private Long officeId; // 部门/组织id
	@ApiModelProperty(value = "部门名称")
	private String officeName;	//部门名称*/
	@ApiModelProperty(value = "角色id")
	private Integer roleId; // 角色id
	@ApiModelProperty(value = "角色名称")
	private String roleName;	//角色名称
	@ApiModelProperty(value = "添加时间")
	private Date createTime; // 添加时间
	@ApiModelProperty(value = "最后登录时间")
	private Date loginTime; // 最后登录时间
	@ApiModelProperty(value = "状态：0启用，1禁用")
	private Integer status; // 状态: 0启用,1禁用
	@ApiModelProperty(value = "备注")
	private String remark; // 备注
	@ApiModelProperty(value = "token")
	private String token;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

/*	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}*/

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return JsonUtils.obj2json(this);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(getRoleId()!=null)
			authorities.add(new SimpleGrantedAuthority((getRoleId()).toString()));
		return authorities;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
