package com.vcmy.common.serivce;

import com.vcmy.entity.OutPortGroup;
import com.vcmy.entity.Port;
import com.vcmy.entity.Strategy;
import com.vcmy.entity.Rule;

import java.util.List;

public interface AssembleCmd {
	List<String> assembleRebootInterface();

	List<String> assembleSelectTimeInterface();

	List<String> assembleUpdateTimeInterface( String timezone, String time);

	List<String> assembleSysInfoInterface();

	List<String> assembleCpuUsageInterface();

	List<String> assembleMemoryUsageInterface();

	String assemblePortScheduleInterface();

	String assembleInsertStrategyInterface(String inPort, OutPortGroup outPortGroup, Rule rule,Integer cookie);

	String assembleDeleteStrategyInterface( String inPort, OutPortGroup outPortGroup,Rule rule);

	List<String> assemblePortInfoScheduleInterface(Port port);

	List<String> assembleUpdatePortScheduleInterface(Port port);

	List<String> assembleSysInfoOtherScheduleInterface();

	List<String> assembleStrategyScheduleInterface();

    String assembleZabbixKeyScheduleInterface();
}
