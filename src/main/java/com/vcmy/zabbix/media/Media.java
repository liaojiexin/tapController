package com.vcmy.zabbix.media;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.ZabbixApiMethod;
import com.vcmy.zabbix.user.UserGetRequest;
import com.vcmy.zabbix.user.UserGetResponse;
import com.vcmy.zabbix.user.UserUpdateRequest;
import com.vcmy.zabbix.user.UserUpdateResponse;

/**
 * @author Suguru Yajima
 */
public class Media extends ZabbixApiMethod {

    public Media(String apiUrl, String auth) {
        super(apiUrl, auth);
    }

    public UserGetResponse get(UserGetRequest request) throws ZabbixApiException {
        UserGetResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, UserGetResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }
    public UserUpdateResponse update (UserUpdateRequest request) throws ZabbixApiException {
        UserUpdateResponse response = null;
        request.setAuth(auth);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, UserUpdateResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

}
