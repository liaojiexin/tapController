package com.vcmy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @ClassName: SdnConfig.java
 * @Description: Sdn控制器相关配置
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月11日 下午4:05:50
 */
@ConfigurationProperties(prefix = "odl")
@Component
@Data
public class OdlConfig {
	/**接口 url*/
	private String url;
	/**接口ip*/
	private String host;
	/**接口端口*/
	private String port;
	/**账号*/
	private String userName;
	/**密码*/
	private String password;
	/**查询拓扑信息*/
	private String topology;
	/** bgp拓扑 **/
	private String bgpTopology;
	/**查询某个设备的某个端口信息*/
	private String topologyNode;
	/**nodes查询*/
	private String nodes;
	/**node查询*/
	private String node;
	/**nodenews查询*/
	private String nodenews;
	/**nodenew查询*/
	private String nodenew;
	/**设备flow*/
	private String nodeFlow1;
	/**设备节点*/
	private String deviceNode;
	/**设备节点*/
	private String deviceNodes;
	/**odl接口获取所有设备基本信息**/
	private String nodeInfos;
	/**设备分组(所有)*/
	private String deviceGroups;
	/**设备分组某个*/
	private String deviceGroup;
	/**终端详情*/
	private String deviceHostAttrbutes;
	/**终端地址映射*/
	private String deviceHostAddressMap;
	/**哑设备*/
	private String deviceDumbs;
	/**应用流表*/
	private String flowManagers;
	/**应用流表*/
	private String flowManager;
	/**Qos*/
	private String qosAll;
	/**Qos*/
	private String qosOne;
	/**meter*/
	private String meterAll;
	/**meter*/
	private String meterOne;
	/**meter*/
	private String meterhttp;
	/**acl*/
	private String aclAll;
	/**acl*/
	private String aclOne;
	/**l3*/
	private String l3All;
	/**l3*/
	private String l3One;
	/**loop*/
	private String loopAll;
	/**loop*/
	private String loopOne;
	/**protocol*/
	private String piAll;
	/**protocol*/
	private String piOne;
	
	/**topo链路切换*/
	private String forward;
	
	private String forwardPath;
	
	/**Defined*/
	private String definedAll;
	/**Defined*/
	private String definedOne;
	
	/**dfAcl*/
	private String dfAclAll;
	/**dfAcl*/
	private String dfAclOne;
	
	/**DLGpath*/
	private String dlgPathAll;
	/**DLGpath*/
	private String dlgPathOne;
	
	/**pathsw*/
	private String pathswAll;
	/**pathsw*/
	private String pathswOne;
	
	/**sfcacl*/
	private String sfcAclAll;
	/**sfcacl*/
	private String sfcAclOne;
	
	/**sfcConfig*/
	private String sfcConfigAll;
	/**sfcConfig*/
	private String sfcConfigOne;
	
	/**sfcDefinedPath*/
	private String sfcDefinedPathAll;
	/**sfcDefinedPath*/
	private String sfcDefinedPathOne;
	
	/**sfcsw*/
	private String sfcswAll;
	/**sfcsw*/
	private String sfcswOne;
	
	/**sfList*/
	private String sfListAll;
	/**sfList*/
	private String sfListOne;
	
	/**networkDomain*/
	private String networkDomainAll;
	/**networkDomain*/
	private String networkDomainOne;
	
	
	/**staticRoute*/
	private String staticRouteAll;
	/**staticRoute*/
	private String staticRouteOne;
	
	private String bgpPeerAll;
	
	private String bgpPeerOne;
	
	/**端口信息统计**/
	private String portStatisticAll;
	
	/**端口信息统计odl接口**/
	private String NodeConnectInfo;
	
	/**平台地址,只配一个**/
	private String platformOne;
	
	private String inventory;
	
	/**链路调度**/
	private String lsAclOne;
	private String lsAclAll;
	private String lsConfigOne;
	private String lsConfigAll;
	private String lsSWOne;
	private String lsSWAll;
	private String lsUseOne;
	
	/**GetOpenflowId**/
	private String getOFnode;
	private String getOfconnector;
	
	/**获取流表统计信息*/
	private String flowStatistic;
	
	/**探测报文*/
	private String packetLink;
	private String packetLinks;
	private String packetDetect;

	private String url1;
}
