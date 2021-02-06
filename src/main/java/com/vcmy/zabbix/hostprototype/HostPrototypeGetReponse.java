package com.vcmy.zabbix.hostprototype;


import com.vcmy.zabbix.ZabbixApiResponse;
import java.util.List;

/**
 * @author Suguru Yajima
 */
public class HostPrototypeGetReponse extends ZabbixApiResponse {
    public HostPrototypeGetReponse() {
        super();
    }

    public List<HostPrototypeObject> result;

    /**
     * Gets result.
     *
     * @return Value of result.
     */
    public List<HostPrototypeObject> getResult() {
        return result;
    }

    /**
     * Sets new result.
     *
     * @param result New value of result.
     */
    public void setResult(List<HostPrototypeObject> result) {
        this.result = result;
    }
}
