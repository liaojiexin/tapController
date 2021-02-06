package com.vcmy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "charge")
@Component
@Data
public class ChargeIpConfig {
	/**计费设备IP1*/
	private String chargeIp1;
	/**计费设备IP2*/
	private String chargeIp2;
	/**计费设备IP3*/
	private String chargeIp3;
}
