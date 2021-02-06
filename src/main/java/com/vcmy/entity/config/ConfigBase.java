package com.vcmy.entity.config;

public class ConfigBase {
	private String sshIp;
	//ssh用户
	private String sshUser;
	//ssh密码
	private String sshPassword;
	
	private String firm;
	//ssh端口
	private int sshPort;
	
	private int switchId;

	private String hostId;

	public String getSshIp() {
		return sshIp;
	}

	public void setSshIp(String sshIp) {
		this.sshIp = sshIp;
	}

	public String getSshUser() {
		return sshUser;
	}

	public void setSshUser(String sshUser) {
		this.sshUser = sshUser;
	}

	public String getSshPassword() {
		return sshPassword;
	}

	public void setSshPassword(String sshPassword) {
		this.sshPassword = sshPassword;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public int getSshPort() {
		return sshPort;
	}

	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}

	public int getSwitchId() {
		return switchId;
	}

	public void setSwitchId(int switchId) {
		this.switchId = switchId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	@Override
	public String toString() {
		return "ConfigBase [sshIp=" + sshIp + ", sshUser=" + sshUser + ", sshPassword=" + sshPassword + ", firm=" + firm
				+ ", sshPort=" + sshPort + ", switchId=" + switchId + "]";
	}


	
}
