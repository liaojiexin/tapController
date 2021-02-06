package com.vcmy.common.serivce.impl;

import com.jcraft.jsch.JSchException;
import com.vcmy.common.SSHShell;
import com.vcmy.common.serivce.SSHCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TapCmd implements SSHCmd {
	private static final Logger logger = LoggerFactory.getLogger(TapCmd.class);
	
	private static final String SETTERMINAL = "screen-length disable";
	private static final String SETCONFIG = "sys";
	
	private static final String EDIT = "root@";
	private static final String ENDFLAG = "]";
	
	private static final String RESEMPTY = "result is empty";
	
	private SSHShell sshShell;

	public TapCmd(String host, int port, String userName, String password) throws JSchException, IOException {
		sshShell = SSHShell.open(host, port, userName, password);
	}

	@Override
	public void runCommand(String command) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runCommands(String command) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void runCommands(List<String> commands) throws Exception {
		if (commands == null || commands.isEmpty()) {
			return ;
		}
		List<String> cmds = new ArrayList<>();
		cmds.addAll(commands);
		
		logger.info("cmd start..");
		cmds.forEach(x->logger.info(x));
		logger.info("cmd end...");
		try {
			sshShell.runCommands(cmds);
		} finally {
			sshShell.close();
		}
		
	}

	@Override
	public String runCommandWithResult(String command) throws Exception {
		if (StringUtils.isEmpty(command)) {
			return null;
		}
		List<String> cmds = new ArrayList<>();
		cmds.add(command);
		try {
			String response = sshShell.runCommands(cmds);
			return response;
		} finally {
			sshShell.close();
		}
	}

	private String analyzeResonse(String response, String command) {
		if (StringUtils.isEmpty(response)) {
			return RESEMPTY;
		}
		int cmdIndex = response.indexOf(command);
		if (cmdIndex < 0) {
			return RESEMPTY;
		}
		int end = findResponseEnd(response);
		if (end < 0 || end < cmdIndex) {
			return RESEMPTY;
		}
		return response.substring(cmdIndex + command.length(), end);
	}

	private int findResponseEnd(String response) {
		int end = response.lastIndexOf(EDIT);
		return end > 0 ? end : response.lastIndexOf(ENDFLAG) - 5;
	}

	@Override
	public String runCommandsWithResult(List<String> command) throws Exception {
		if(command == null ||command.isEmpty()){
			return null;
		}
		List<String> cmds = new ArrayList<>();
//		cmds.add(SETTERMINAL);
		cmds.addAll(command);
		String last = command.get(0);
		try {
			String response = sshShell.runCommands(cmds);
			return analyzeResonse(response,last);
		} finally {
			sshShell.close();
		}
	}

	

	@Override
	public String runCommandsJudgeReturn(List<String> commands) throws Exception {
		if (commands == null || commands.isEmpty()) {
			return "";
		}
		List<String> cmds = new ArrayList<>();
		cmds.add(SETCONFIG);
		cmds.addAll(commands);
		
		logger.info("cmd start..");
		cmds.forEach(x->logger.info(x));
		logger.info("cmd end...");
		try {
			return sshShell.runCommands(cmds);
		} finally {
			sshShell.close();
		}
	}

}
