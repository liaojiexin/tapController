package com.vcmy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "sflow")
@Component
@Data
public class SflowConfig {
	
	/**接口 url*/
	private String url;
	/**接口ip*/
	private String host;
	/**接口端口*/
	private String port;
	
	//定义流
	private String addFlow;
	
	//删除流
	private String deleteFlow;
	
	//定义阈值
	private String addThreshold;
	
	//删除阈值
	private String deleteThreshold;
	
	//定义group
	private String addGroup;
	
	//group
	private String deleteGroup;
	
	//获取所有event
	private String getAllEvents;
	
	//获取所有activeflows
	private String  getActiveflows;
	
	

}
