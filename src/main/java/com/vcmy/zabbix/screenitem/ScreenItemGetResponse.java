package com.vcmy.zabbix.screenitem;


import com.vcmy.zabbix.ZabbixApiResponse;
import java.util.List;

/**
 * @author Suguru Yajima
 */
public class ScreenItemGetResponse extends ZabbixApiResponse {
    private List<ScreenItemObject> result;

    public ScreenItemGetResponse() {
        super();
    }

    /**
     * Gets result.
     *
     * @return Value of result.
     */
    public List<ScreenItemObject> getResult() {
        return result;
    }

    /**
     * Sets new result.
     *
     * @param result New value of result.
     */
    public void setResult(List<ScreenItemObject> result) {
        this.result = result;
    }
}
