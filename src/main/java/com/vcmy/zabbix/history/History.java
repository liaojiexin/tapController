package com.vcmy.zabbix.history;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.ZabbixApiMethod;

import java.util.Date;

/**
 * Created by Suguru Yajima on 2014/06/03.
 */
public class History extends ZabbixApiMethod {

    public History(String apiUrl, String auth) {
        super(apiUrl, auth);
    }

    public HistoryGetResponse get(HistoryGetRequest request) throws ZabbixApiException {
        HistoryGetResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);
            response = gson.fromJson(responseJson, HistoryGetResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }
        return response;
    }
}
