package com.vcmy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @ClassName: H3cConfig.java
 * @Description: h3c控制器相关配置
 * @version: 1.0.0
 * @author Tristan
 * @date: 2019年12月23日 上午10:05:50
 */
@Data
@ConfigurationProperties(prefix = "h3c")
@Component
public class H3cConfig {
	/** 接口 url */
	private String url;
	/** 账号 */
	private String h3cUserName;
	/** 密码 */
	private String h3cPassword;
	
	/** 获取h3c节点信息 */
	private String h3cNode;
	
	/** 获取h3c链路信息 */
	private String h3cLink;

	/** 获取h3c中admin的useId */
	private String h3cUseId;
	
	/** 获取h3c中bgp-ls配置 */
	private String h3cAsConfig;
	

	/** 节点标签 */
	private String nodeLabel;

	/** 设置标签范围 */
	private String setLabelRange;

	/** 获取标签范围 */
	private String getLabelRange;

	/** 设置标签栈深度 */
	private String setStackDepth;

	/** 获取标签栈深度 */
	private String getStackDepth;

	/** 分配全局标签 */
	private String assignLabel;

	/** 取消分配全局标签 */
	private String unAssignLabel;

	/** 获取标签分配记录 */
	private String getLabelRecord;

	/** 获取标签数量信息 */
	private String getLabelCount;	
	
	
	
	/**获取link质量信息  */
	private String getLinkQuality;
	
	/**获取链路/隧道上的实时带宽  */
	private String getLinkBandwidth;
	
	/**获取指定link上的应用组带宽信息  */
	private String getLinkFlowGroupBandwidth;
	
	/**获取链路质量的历史信息  */
	private String getHistoryLinkQuality;
	
	/**获取链路带宽的历史信息  */
	private String getHistoryLinkBandwidth;
	
	/**获取指定link上的应用组带宽历史信息  */
	private String getLinkFgHistoryBandwidth;
	
	/**清除历史数据  */
	private String clearHistoryData;
	
	/**获取指定应用组实例的路径带宽信息  */
	private String getFgInstanceBandwidth;
	
	/**获取全网链路实时流量TopN排名  */
	private String getLinkBandwidthTopN;
	
	/**获取全网链路历史流量TopN排名  */
	private String getLinkHistoryTrafficTopN;
	
	/**获取所有链路质量的历史信息  */
	private String getAllLinkHistoryQuality;
	
	/**获取所有链路质量的历史信息报表  */
	private String getAllLinkHistoryQualityReport;
	
	/**获取所有链路带宽的历史信息  */
	private String getAllLinkHistoryBandwidthReport;

	
	
}
