package com.vcmy.zabbix.script;


import com.vcmy.zabbix.ZabbixApiRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Suguru Yajima
 */
public class ScriptDeleteRequest extends ZabbixApiRequest {
    private List<Integer> params = new ArrayList<Integer>();

    public ScriptDeleteRequest() {
        setMethod("script.delete");
    }

    public void addScriptId(Integer id) {
        params.add(id);
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
