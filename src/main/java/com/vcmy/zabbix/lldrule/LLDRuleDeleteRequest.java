package com.vcmy.zabbix.lldrule;

import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.utils.ZbxListUtils;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class LLDRuleDeleteRequest extends ZabbixApiRequest {

    private List<Integer> params;

    public LLDRuleDeleteRequest() {
        setMethod("discoveryrule.delete");
    }

    public List<Integer> getParams() {
        return params;
    }

    public void setParams(List<Integer> params) {
        this.params = params;
    }

    public void addruleId(Integer id) {
        params = ZbxListUtils.add(params, id);
    }
}
