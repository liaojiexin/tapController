package com.vcmy.zabbix.trend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.ZabbixApiMethod;

/**
 * Created by Suguru Yajima on 2014/06/03.
 */
public class Trend extends ZabbixApiMethod {

    public Trend(String apiUrl, String auth) {
        super(apiUrl, auth);
    }

    public TrendGetResponse get(TrendGetRequest request) throws ZabbixApiException {
        TrendGetResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, TrendGetResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }
        return response;
    }
}
