package com.vcmy.common.serivce;

import com.vcmy.entity.OutPortGroup;
import com.vcmy.entity.Port;
import com.vcmy.entity.Strategy;
import com.vcmy.entity.Rule;
import com.vcmy.entity.config.*;

public interface SSHConfig {

	/** 系统重启*/
	String reboot(ConfigInterface configInterface);

	/** 查询系统时间*/
	String selectTime(ConfigInterface configInterface);

	/** 修改系统系统*/
	String updateTime(ConfigInterface configInterface, String timezone, String time);

	/** 总览-查看系统信息和状态*/
	String sysInfo(ConfigInterface configInterface);

	/** 总览-查看端口状态up和down*/
	String portSchedule(ConfigInterface configInterface);

	/** 策略-添加新策略*/
	String insertStrategy(ConfigInterface configInterface, String inPort, OutPortGroup outPortGroup, Rule rule,Integer cookie) throws CloneNotSupportedException;

	/** 策略-删除策略*/
	String deleteStrategy(ConfigInterface configInterface, String inPort, OutPortGroup outPortGroup,Rule rule) throws CloneNotSupportedException;

	/** 策略-端口其他信息*/
	String portInfoSchedule(ConfigInterface configInterface,Port port);

	/** 策略-修改端口*/
	String updatePort(ConfigInterface configInterface, Port port);

	/** 总览-查看温度、风扇、电压*/
	String sysInfoOther(ConfigInterface configInterface);

	/** 定时查找设备策略*/
	String strategySchedule(ConfigInterface configInterface);

	/** 查找sflow和zabbix设定好的键值*/
    String zabbixKeySchedule(ConfigInterface configInterface);
}
