package com.vcmy.zabbix.user;


import com.vcmy.zabbix.ZabbixApiResponse;

/**
 * @ClassName UserLogoutResponse
 * @Description TODO
 * @Author xjq
 * @Date 2018/6/8 11:12
 * @Version 1.0
 **/
public class UserLogoutResponse extends ZabbixApiResponse {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
