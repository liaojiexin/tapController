package com.vcmy.common.serivce.impl;

import com.alibaba.fastjson.JSONObject;
import com.vcmy.common.serivce.AssembleCmd;
import com.vcmy.entity.OutPortGroup;
import com.vcmy.entity.Port;
import com.vcmy.entity.Strategy;
import com.vcmy.entity.Rule;
import com.vcmy.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liaojiexin
 * @Description tap设备命令
 * @Date 2020/12/16 13:25
 * @Param
 * @return
 **/
@Component
public class TapAssembleCmd implements AssembleCmd {

	
	private static final String QUIT = "quit";

	@Override
	public List<String> assembleRebootInterface() {
		List<String> commands = new ArrayList<>();
		commands.add("reboot");	//重新启动
		return commands;
	}

	@Override
	public List<String> assembleSelectTimeInterface() {
		List<String> commands =new ArrayList<>();
		commands.add("date");		//系统当前时间
		return commands;
	}

	@Override
	public List<String> assembleUpdateTimeInterface( String timezone, String time) {
		List<String> commands =new ArrayList<>();
		commands.add("timedatectl set-ntp 0"); //关闭ntp时间同步
		if(timezone!=null)
			commands.add(CommonUtils.parseUri("timedatectl set-timezone ?",timezone));		//修改时区
		if (time!=null)
			commands.add(CommonUtils.parseUri("date -s \"?\"",time));	//修改系统时间
		commands.add("hwclock --systohc");
		return commands;
	}

	@Override
	public List<String> assembleSysInfoInterface() {
		List<String> commands=new ArrayList<>();
		commands.add("show version");	//系统信息
		return commands;
	}

	@Override
	public List<String> assembleCpuUsageInterface() {
		List<String> commands=new ArrayList<>();
		commands.add("echo 100.00 - `mpstat -P 0 | awk 'END{print $NF}'` | bc");	//CPU使用率
		return commands;
	}

	@Override
	public List<String> assembleMemoryUsageInterface() {
		List<String> commands=new ArrayList<>();
		commands.add("free -m | sed -n '2p' | awk '{printf (\"%.2f\\n\", $3/$2*100)}'");	//内存使用率
		return commands;
	}

	@Override
	public String assemblePortScheduleInterface() {
		String commands="ovsdb-client dump Open_vSwitch Interface name link_state | grep eth";	//查看端口状态up和down
		return commands;
	}

	@Override
	public String assembleInsertStrategyInterface(String inPort, OutPortGroup outPortGroup, Rule rule,Integer cookie) {
		String commands=new String();
		String match = new String();	//匹配域
		String action =new String();		//动作

		//匹配域处理
		List<String> matchList=new ArrayList<>();
		matchList.add("cookie="+cookie);		//加上编号,加到设备后会变成十六进制
		if(!StringUtils.isBlank(inPort))			//入端口
			matchList.add("in_port="+inPort);
		if (rule!=null && !JSONObject.toJSONString(rule).equals("{}")){
			if (rule.getVlanRange()!=null)	//vlan范围
				matchList.add("dl_vlan="+ rule.getVlanRange());
			if(!StringUtils.isBlank(rule.getSourceMac()))	//源mac
				matchList.add("dl_src="+ rule.getSourceMac());
			if(!StringUtils.isBlank(rule.getDestinationMac()))	//目的mac
				matchList.add("dl_dst="+ rule.getDestinationMac());
			if(rule.getMplsLabel()!=null)	//MPLS 标签
				matchList.add("mpls,mpls_label="+ rule.getMplsLabel());
			if(rule.getMplsTc()!=null)	//MPLS tc
				matchList.add("mpls,mpls_tc="+ rule.getMplsTc());
			if(!StringUtils.isBlank(rule.getSourceIp()))	//源IP
				matchList.add("ip,nw_src="+ rule.getSourceIp());
			if(!StringUtils.isBlank(rule.getDestinationIp()))	//目的IP
				matchList.add("ip,nw_dst="+ rule.getDestinationIp());
			if(!StringUtils.isBlank(rule.getProtocol()))	//协议
			{
				if (rule.getProtocol().equals("TCP"))
					matchList.add("ip,nw_proto=6");
				if (rule.getProtocol().equals("UDP"))
					matchList.add("ip,nw_proto=17");
			}
			if(rule.getSourcePort()!=null)	//源端口
			{
				if(rule.getProtocol().equals("TCP"))
					matchList.add("tcp,tcp_src="+ rule.getSourcePort());
				if(rule.getProtocol().equals("UDP"))
					matchList.add("udp,udp_src="+ rule.getSourcePort());
			}
			if(rule.getDestinationPort()!=null)	//目的端口
			{
				if(rule.getProtocol().equals("TCP"))
					matchList.add("tcp,tcp_dst="+ rule.getDestinationPort());
				if(rule.getProtocol().equals("UDP"))
					matchList.add("udp,udp_dst="+ rule.getDestinationPort());
			}
			if(!StringUtils.isBlank(rule.getTcpFlag()))	//TCP flag
				matchList.add("tcp,tcp_flags="+ rule.getTcpFlag());
		}
		for (String a:matchList) {
			match=match+a+",";
		}


		//动作处理
		List<String> actionList=new ArrayList<>();
		if(((rule!=null && !JSONObject.toJSONString(rule).equals("{}")) &&(!StringUtils.isBlank(rule.getVlanAction()) || rule.getVlanId()!=null
				|| rule.getMplsActionLabel()!=null || !StringUtils.isBlank(rule.getMplsAction()) || !StringUtils.isBlank(rule.getUpdateSourceMac())
				|| rule.getMessageTruncate()!=null || !StringUtils.isBlank(rule.getUpdateDestinationMac()) || !StringUtils.isBlank(rule.getUpdateSourceIp())
				|| !StringUtils.isBlank(rule.getUpdateDestinationIp())))||  !JSONObject.toJSONString(outPortGroup.getOutPortGroup()).equals("{}")){
			action="actions=";
		}
		if (rule!=null && !JSONObject.toJSONString(rule).equals("{}")){
			if(!StringUtils.isBlank(rule.getVlanAction()))		//VLAN动作
			{
				if(rule.getVlanId()!=null){
					if(rule.getVlanAction().equals("新增VLAN"))
						actionList.add("push_vlan:0x8100,set_field:"+(rule.getVlanId()+4096)+"->vlan_vid");
					if(rule.getVlanAction().equals("删除VLAN"))
						actionList.add("strip_vlan");
					if(rule.getVlanAction().equals("修改VLAN"))
						actionList.add("mod_vlan_vid:"+ rule.getVlanId());
				}
			}
			if (!StringUtils.isBlank(rule.getMplsAction())){		//MPLS动作
				if (rule.getMplsActionLabel()!=null){
					if(rule.getMplsAction().equals("新增标签"))
						actionList.add("push_mpls:0x8847,set_field:"+ rule.getMplsActionLabel()+"->mpls_label");
					if(rule.getMplsAction().equals("删除标签"))
						actionList.add("pop_mpls:0x0800");
					if(rule.getMplsAction().equals("修改标签"))
						actionList.add("set_mpls_label:"+ rule.getMplsActionLabel());
				}
			}
			if(!StringUtils.isBlank(rule.getUpdateSourceMac()))			//修改源mac
				actionList.add("mod_dl_src:"+ rule.getUpdateSourceMac());
			if(!StringUtils.isBlank(rule.getUpdateDestinationMac()))		//修改目的mac
				actionList.add("mod_dl_dst:"+ rule.getUpdateDestinationMac());
			if (!StringUtils.isBlank(rule.getUpdateSourceIp()))		//修改源ip
				actionList.add("mod_nw_src:"+ rule.getUpdateSourceIp());
			if(!StringUtils.isBlank(rule.getUpdateDestinationIp()))		//修改目的ip
				actionList.add("mod_nw_dst:"+ rule.getUpdateDestinationIp());

		}
		if(!JSONObject.toJSONString(outPortGroup.getOutPortGroup()).equals("{}") && outPortGroup.getOutPortGroup()!=null){	//出端口组
			for (Port outPort:outPortGroup.getOutPortGroup()) {
				if(rule!=null &&!JSONObject.toJSONString(rule).equals("{}") && rule.getMessageTruncate()!=null)		//报文截短
					actionList.add("output(port="+outPort.getPortName()+",max_len="+ rule.getMessageTruncate()+")");
				else
					actionList.add("output:"+outPort.getPortName());
			}
		}
		for (String a:actionList){
			action=action+a+",";
		}


		//动作和匹配一起处理
		String parameter=(match+action).substring(0,(match+action).length() - 1);
		commands=CommonUtils.parseUri("ovs-ofctl add-flow -O OpenFlow13 br100 \" ? \"",parameter);	//内存使用率
		return commands;
	}

	@Override
	public String assembleDeleteStrategyInterface( String inPort, OutPortGroup outPortGroup,Rule rule) {

		String commands=new String();

		List<String> commandList=new ArrayList<>();
		if(!StringUtils.isBlank(inPort))			//入端口
			commandList.add("in_port="+inPort);
		if(rule!=null){
			if (rule.getVlanRange()!=null)	//vlan范围
				commandList.add("dl_vlan="+ rule.getVlanRange());
			if(!StringUtils.isBlank(rule.getSourceMac()))	//源mac
				commandList.add("dl_src="+ rule.getSourceMac());
			if(!StringUtils.isBlank(rule.getDestinationMac()))	//目的mac
				commandList.add("dl_dst="+ rule.getDestinationMac());
			if(rule.getMplsLabel()!=null)	//MPLS 标签
				commandList.add("mpls,mpls_label="+ rule.getMplsLabel());
			if(rule.getMplsTc()!=null)	//MPLS tc
				commandList.add("mpls,mpls_tc="+ rule.getMplsTc());
			if(!StringUtils.isBlank(rule.getSourceIp()))	//源IP
				commandList.add("nw_src="+ rule.getSourceIp());
			if(!StringUtils.isBlank(rule.getDestinationIp()))	//目的IP
				commandList.add("nw_dst="+ rule.getDestinationIp());
			if(!StringUtils.isBlank(rule.getProtocol()))	//协议
			{
				if (rule.getProtocol().equals("TCP"))
					commandList.add("nw_proto=6");
				if (rule.getProtocol().equals("UDP"))
					commandList.add("nw_proto=17");
			}
			if(rule.getSourcePort()!=null)	//源端口
			{
				if(rule.getProtocol().equals("TCP"))
					commandList.add("tcp,tcp_src="+ rule.getSourcePort());
				if(rule.getProtocol().equals("UDP"))
					commandList.add("udp,udp_src="+ rule.getSourcePort());
			}
			if(rule.getDestinationPort()!=null)	//目的端口
			{
				if(rule.getProtocol().equals("TCP"))
					commandList.add("tcp,tcp_dst="+ rule.getDestinationPort());
				if(rule.getProtocol().equals("UDP"))
					commandList.add("udp,udp_dst="+ rule.getDestinationPort());
			}
			if(!StringUtils.isBlank(rule.getTcpFlag()))	//TCP flag
				commandList.add("tcp,tcp_flags="+ rule.getTcpFlag());
		}
		if(outPortGroup.getOutPortGroup().size()>0 && !JSONObject.toJSONString(outPortGroup.getOutPortGroup()).equals("[{}]")){	//出端口
			for (Port outPort:outPortGroup.getOutPortGroup()) {
				commandList.add("out_port="+outPort.getPortName());
			}
		}
		for (String a:commandList){
			commands=commands+a+",";
		}


		//动作和匹配一起处理
		String parameter=commands.substring(0,(commands).length() - 1);
		commands=CommonUtils.parseUri("ovs-ofctl del-flows br100 \" ? \"",parameter);	//内存使用率
		return commands;
	}

	@Override
	public List<String> assemblePortInfoScheduleInterface(Port port) {
		List<String> commands=new ArrayList<>();
		commands.add(CommonUtils.parseUri("ovs-vsctl get interface ? status:link_speed",port.getPortName()));//端口属性
		commands.add(CommonUtils.parseUri("ovs-vsctl get interface ? statistics:rx_packets",port.getPortName()));	//端口统计-接收包数
		commands.add(CommonUtils.parseUri("ovs-vsctl get interface ? statistics:tx_packets",port.getPortName()));	//端口统计-发送包数
		commands.add(CommonUtils.parseUri(" /home/vcmyos/port.sh ?",port.getPortName()));	//端口统计-收发速率
		return commands;
	}

	@Override
	public List<String> assembleUpdatePortScheduleInterface(Port port) {
		List<String> commands=new ArrayList<>();
		if(!StringUtils.isBlank(port.getState()))
			commands.add(CommonUtils.parseUri("ovs-ofctl mod-port br100 ? ?",port.getPortName(),port.getState()));	//改变端口开关状态
		if(!StringUtils.isBlank(port.getRate())){
			if(port.getRate().equals("1000"))
				port.setRate("1G");
			if(port.getRate().equals("10000"))
				port.setRate("10G");
			commands.add(CommonUtils.parseUri("ovs-vsctl set Interface ? options:link_speed=?",port.getPortName(),port.getRate()));	//改变端口速率（好像是自适应的）
		}
		return commands;
	}

	@Override
	public List<String> assembleSysInfoOtherScheduleInterface() {
		List<String> commands=new ArrayList<>();
		commands.add("sensors |grep -i 'SYSTIN'|awk -F ':' '{print $2}'|awk -F ' ' '{print $1$2}'");		//查看交换芯片温度
		commands.add("sensors |grep -i 'CORE 0'|awk -F ':' '{print $2}'|awk -F ' ' '{print $1$2}'");		//查看CPU核心温度
		commands.add("sensors |grep -i 'AUXTIN'|awk -F ':' '{print $2}'|awk -F ' ' '{print $1$2}'");		//前面板进风口温度
		commands.add("sensors |grep -i -w 'fan1:' |awk -F ':' '{print $2}' |awk -F ' ' '{print $1,$2}'| awk 'NR==2'");		//风扇
		commands.add("sensors |grep -i -w 'fan2:' |awk -F ':' '{print $2}' |awk -F ' ' '{print $1,$2}'| awk 'NR==2'");		//风扇
		commands.add("sensors |grep -i -w 'fan3:' |awk -F ':' '{print $2}' |awk -F ' ' '{print $1,$2}'| awk 'NR==2'");		//风扇
		commands.add("sensors |grep -i 'Vcore'|awk -F ' ' '{print $2$3}'|awk 'NR == 1'");	//AC核心电压
		return commands;
	}

	@Override
	public List<String> assembleStrategyScheduleInterface() {
		List<String> commands=new ArrayList<>();
		commands.add("ovs-ofctl dump-flows br100");		//查找设备上所有策略
		return commands;
	}

	@Override
	public String assembleZabbixKeyScheduleInterface() {
		String command="curl -sX GET  \"http://127.0.0.1:8008/dump/ALL/ifname/json\"";
		return command;
	}
}