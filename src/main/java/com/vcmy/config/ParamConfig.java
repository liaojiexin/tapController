
package com.vcmy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @类名
 * @作者 yaojingbai
 * @日期 2018年8月14日
 * @版本 1.0
 * @描述 配置文件参数值统一获取工具类
 */
@Component
public class ParamConfig {

	@Value("${zabbix.licensePath}")
	private String licensePath;
	
    @Value("${zabbix.user}")
    private String user;
    
    @Value("${zabbix.pwd}")
    private String password;
    
    @Value("${zabbix.userid}")
    private Integer userId;
    
    @Value("${zabbix.apiurl}")
    private String apiUrl;
    
    @Value("${host.groupid}")
	private Integer groupId;
    
	@Value("${host.templateid}")
	private Integer templateid;

	public String getLicensePath() {
		return licensePath;
	}

	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Integer templateid) {
		this.templateid = templateid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
