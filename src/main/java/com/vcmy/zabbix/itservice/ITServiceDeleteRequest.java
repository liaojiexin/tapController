package com.vcmy.zabbix.itservice;


import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.utils.ZbxListUtils;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class ITServiceDeleteRequest extends ZabbixApiRequest {

    private List<Integer> params;

    public ITServiceDeleteRequest() {
        setMethod("service.delete");
    }

    public void addServiceId(Integer id) {
        params = ZbxListUtils.add(params, id);
    }

    /**
     * Gets params.
     *
     * @return Value of params.
     */
    public List<Integer> getParams() {
        return params;
    }

    /**
     * Sets new params.
     *
     * @param params New value of params.
     */
    public void setParams(List<Integer> params) {
        this.params = params;
    }
}
