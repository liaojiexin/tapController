package com.vcmy.common;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Device
 * @Description: TODO   设备
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/20 19:50
 */
@Component
public class Device {
    @Value("${device.Ip}")
    public String Ip;

    @Value("${device.User}")
    public String User;

    @Value("${device.Password}")
    public String Password;

    @Value("${device.Port}")
    public Integer Port;

    @Value("${device.HostId}")
    public Integer HostId;

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getPort() {
        return Port;
    }

    public void setPort(Integer port) {
        Port = port;
    }

    public Integer getHostId() {
        return HostId;
    }

    public void setHostId(Integer hostId) {
        HostId = hostId;
    }

}
