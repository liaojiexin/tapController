package com.vcmy.common.serivce.impl;

import com.vcmy.common.serivce.AssembleCmd;
import com.vcmy.common.serivce.SSHCmd;
import com.vcmy.dao.PortDao;
import com.vcmy.dao.RuleDao;
import com.vcmy.entity.OutPortGroup;
import com.vcmy.entity.Port;
import com.vcmy.entity.Strategy;
import com.vcmy.entity.Rule;
import com.vcmy.entity.config.*;
import com.vcmy.common.serivce.SSHConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SSHConfigImpl implements SSHConfig {

    @Autowired
    private PortDao portDao;

    @Autowired
    private RuleDao ruleDao;

	@Override
	public String reboot(ConfigInterface configInterface) {
		AssembleCmd assembleCmd = new TapAssembleCmd();
		List<String> commands = assembleCmd.assembleRebootInterface();
		try{
			SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
			ssh.runCommands(commands);
		}catch (Exception e) {
			throw new RuntimeException("系统还未启动");
		}
		return null;
	}

    @Override
    public String selectTime(ConfigInterface configInterface) {
        AssembleCmd assembleCmd = new TapAssembleCmd();
        List<String> commands = assembleCmd.assembleSelectTimeInterface();
        try{
            SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
             return ssh.runCommandsWithResult(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateTime(ConfigInterface configInterface, String timezone, String time) {
	    AssembleCmd assembleCmd=new TapAssembleCmd();
	    List<String> commands =assembleCmd.assembleUpdateTimeInterface(timezone,time);
        try{
            SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            ssh.runCommands(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String sysInfo(ConfigInterface configInterface) {
        AssembleCmd assembleCmd = new TapAssembleCmd();
        List<String> commands1 = assembleCmd.assembleSysInfoInterface();
        List<String> commands2 = assembleCmd.assembleCpuUsageInterface();
        List<String> commands3 = assembleCmd.assembleMemoryUsageInterface();
        try{
            SSHCmd ssh1 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            String result1=ssh1.runCommandsWithResult(commands1);
            SSHCmd ssh2 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            String result2=ssh2.runCommandsWithResult(commands2).replace("\r\n","");
            if(result2.substring(0,1).equals("."))
                result2="0"+result2;
            SSHCmd ssh3 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            String result3=ssh3.runCommandsWithResult(commands3).replace("\r\n","");
            return result1+"CpuUsage:"+result2+"\r\nMemoryUsage:"+result3;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String portSchedule(ConfigInterface configInterface) {
        AssembleCmd assembleCmd=new TapAssembleCmd();
        String commands =assembleCmd.assemblePortScheduleInterface();
        try{
            SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            return ssh.runCommandWithResult(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String insertStrategy(ConfigInterface configInterface, String inPort, OutPortGroup outPortGroup , Rule rule,Integer cookie) throws CloneNotSupportedException {
        //端口处理,将端口id转化为端口名称
        //出端口
        for(Port outPort:outPortGroup.getOutPortGroup()){
            String portName =portDao.selectPortNameById(outPort.getPortId());
            outPort.setPortName(portName);
        }

        AssembleCmd assembleCmd=new TapAssembleCmd();
        String commands =assembleCmd.assembleInsertStrategyInterface(inPort,outPortGroup, rule,cookie);
        try{
            SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            return ssh.runCommandWithResult(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteStrategy(ConfigInterface configInterface, String inPort, OutPortGroup outPortGroup,Rule rule) throws CloneNotSupportedException {
        AssembleCmd assembleCmd=new TapAssembleCmd();
        String commands =assembleCmd.assembleDeleteStrategyInterface(inPort,outPortGroup,rule);
        try{
            SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            return ssh.runCommandWithResult(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String portInfoSchedule(ConfigInterface configInterface,Port port) {
        AssembleCmd assembleCmd=new TapAssembleCmd();
        List<String> commands =assembleCmd.assemblePortInfoScheduleInterface(port);
        try{
            SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            return ssh.runCommandsWithResult(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updatePort(ConfigInterface configInterface, Port port) {
        AssembleCmd assembleCmd=new TapAssembleCmd();
        List<String> commands =assembleCmd.assembleUpdatePortScheduleInterface(port);
        try{
            SSHCmd ssh = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            return ssh.runCommandsWithResult(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sysInfoOther(ConfigInterface configInterface) {
        AssembleCmd assembleCmd=new TapAssembleCmd();
        List<String> commands =assembleCmd.assembleSysInfoOtherScheduleInterface();
        try{
            SSHCmd ssh1 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            SSHCmd ssh2 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            SSHCmd ssh3 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            SSHCmd ssh4 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            SSHCmd ssh5 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            SSHCmd ssh6 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            SSHCmd ssh7 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            String command1=ssh1.runCommandWithResult(commands.get(0)).substring(443);
            String command2=ssh2.runCommandWithResult(commands.get(1)).substring(443);
            String command3=ssh3.runCommandWithResult(commands.get(2)).substring(443);
            String command4=ssh4.runCommandWithResult(commands.get(3)).substring(443);
            String command5=ssh5.runCommandWithResult(commands.get(4)).substring(443);
            String command6=ssh6.runCommandWithResult(commands.get(5)).substring(443);
            String command7=ssh7.runCommandWithResult(commands.get(6)).substring(443);
            return command1+command2+command3+command4+command5+command6+command7;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String strategySchedule(ConfigInterface configInterface) {
        AssembleCmd assembleCmd=new TapAssembleCmd();
        List<String> commands=assembleCmd.assembleStrategyScheduleInterface();
        try{
            SSHCmd ssh1 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            return ssh1.runCommandsWithResult(commands);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String zabbixKeySchedule(ConfigInterface configInterface) {
        AssembleCmd assembleCmd=new TapAssembleCmd();
        String command=assembleCmd.assembleZabbixKeyScheduleInterface();
        try{
            SSHCmd ssh1 = new TapCmd(configInterface.getSshIp(),configInterface.getSshPort(),configInterface.getSshUser(),configInterface.getSshPassword());
            return ssh1.runCommandWithResult(command);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
