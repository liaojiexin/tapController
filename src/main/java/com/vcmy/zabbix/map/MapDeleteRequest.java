package com.vcmy.zabbix.map;


import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.utils.ZbxListUtils;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class MapDeleteRequest extends ZabbixApiRequest {

    private List<Integer> params;

    public MapDeleteRequest() {
        setMethod("map.delete");
    }

    public void addMapId(Integer id) {
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
