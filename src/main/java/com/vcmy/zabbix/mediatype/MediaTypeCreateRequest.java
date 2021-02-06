package com.vcmy.zabbix.mediatype;


import com.vcmy.zabbix.ZabbixApiRequest;
import com.vcmy.zabbix.utils.ZbxListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Suguru Yajima
 */
public class MediaTypeCreateRequest extends ZabbixApiRequest {

    private List<MediaTypeObject> params = new ArrayList<MediaTypeObject>();

    public MediaTypeCreateRequest() {
        setMethod("mediatype.create");
    }

    public void addMediaType(MediaTypeObject obj) {
        params = ZbxListUtils.add(params, obj);
    }

    /**
     * Gets params.
     *
     * @return Value of params.
     */
    public List<MediaTypeObject> getParams() {
        return params;
    }

    /**
     * Sets new params.
     *
     * @param params New value of params.
     */
    public void setParams(List<MediaTypeObject> params) {
        this.params = params;
    }
}
