package com.vcmy.common.serivce;

import java.util.List;

public interface SSHCmd {
	
	/**
	 * @Title: runCommand 
	 * @Description: 以shell方式执行一条普通配置的命令 
	 * @return String    返回类型 
	 */
	 void runCommand(String command) throws Exception;
	/**
	 * @Title: runCommands 
	 * @Description: 多条命令写成一行执行
	 * @return String    返回类型 
	 */
	 void runCommands(String command) throws Exception;
	/**
	 * @Title: runCommands 
	 * @Description: 多条命令一行执行
	 * @return String    返回类型 
	 */
	 void runCommands(List<String> commands) throws Exception;
	
	 String runCommandsJudgeReturn(List<String> commands) throws Exception;
	/**
	 * @Title: runCommandWithResult 
	 * @Description: 执行命令同时返回执行结果
	 * @return String    返回类型 
	 */
	 String runCommandWithResult(String command)throws Exception;
	
	 String runCommandsWithResult(List<String> command)throws Exception;
	
}
