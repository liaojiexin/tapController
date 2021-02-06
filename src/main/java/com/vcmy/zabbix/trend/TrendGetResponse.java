package com.vcmy.zabbix.trend;


import com.vcmy.zabbix.ZabbixApiResponse;

import java.util.List;

/**
 * Created by Suguru Yajima on 2014/06/03.
 */
public class TrendGetResponse extends ZabbixApiResponse {
    private List<TrendObject> result;

    public TrendGetResponse() {
        super();
    }

    public List<TrendObject> getResult() {
        return result;
    }

    public void setResult(List<TrendObject> result) {
        this.result = result;
    }
}
