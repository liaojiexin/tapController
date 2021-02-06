package com.vcmy.zabbix.history;


import com.vcmy.zabbix.ZabbixApiResponse;

import java.util.List;

/**
 * Created by Suguru Yajima on 2014/06/03.
 */
public class HistoryGetResponse extends ZabbixApiResponse {
    private List<HistoryObject> result;

    public HistoryGetResponse() {
        super();
    }

    public List<HistoryObject> getResult() {
        return result;
    }

    public void setResult(List<HistoryObject> result) {
        this.result = result;
    }
}
