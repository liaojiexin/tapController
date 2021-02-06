package com.vcmy.zabbix.proxy;

import com.google.gson.annotations.SerializedName;
import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.utils.ZbxListUtils;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class ProxyUpdateRequest extends ZabbixApiRequest {
    private Params params = new Params();

    public ProxyUpdateRequest() {
        setMethod("proxy.update");
    }

    /**
     * Gets params.
     *
     * @return Value of params.
     */
    public Params getParams() {
        return params;
    }

    /**
     * Sets new params.
     *
     * @param params New value of params.
     */
    public void setParams(Params params) {
        this.params = params;
    }

    public class Params extends ProxyObject {

        private List<Integer> hosts;
        @SerializedName("interface")
        private ProxyInterfaceObject proxyInterface;

        public void addHostId(Integer id) {
            hosts = ZbxListUtils.add(hosts, id);
        }

    }
}
