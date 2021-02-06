package com.vcmy.zabbix.media;


import com.vcmy.zabbix.ZabbixApiResponse;

import java.util.List;

/**
 * @author Suguru Yajima
 */
public class MediaGetResponse extends ZabbixApiResponse {
    private List<MediaObject> result;

    public MediaGetResponse() {
        super();
    }

    /**
     * Gets result.
     *
     * @return Value of result.
     */
    public List<MediaObject> getResult() {
        return result;
    }

    /**
     * Sets new result.
     *
     * @param result New value of result.
     */
    public void setResult(List<MediaObject> result) {
        this.result = result;
    }
}
