package com.vcmy.entity.config;

public class ConfigInterface extends ConfigBase{
	//以太网
	private String ethernet;
	//地址
	private String address;
	//描述
	private String description;

	/**
	 * @return the ethernet
	 */
	public String getEthernet() {
		return ethernet;
	}

	/**
	 * @param ethernet the ethernet to set
	 */
	public void setEthernet(String ethernet) {
		this.ethernet = ethernet;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
