package com.vcmy.zabbix.user;


import com.vcmy.zabbix.ZabbixApiRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserLogoutRequest
 * @Description TODO
 * @Author xjq
 * @Date 2018/6/8 11:08
 * @Version 1.0
 **/
public class UserLogoutRequest extends ZabbixApiRequest {
    private List<Integer> params = new ArrayList<>();
    public UserLogoutRequest(String auth){
        setMethod("user.logout");
        setAuth(auth);
    }
}
