package com.vcmy.zabbix.lldrule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.ZabbixApiMethod;

/**
 * Created by Suguru Yajima on 2014/06/06.
 */
public class LLDRule extends ZabbixApiMethod {

    public LLDRule(String apiUrl, String auth) {
        super(apiUrl, auth);
    }

    public LLDRuleCreateResponse create(LLDRuleCreateRequest request) throws ZabbixApiException {
        LLDRuleCreateResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, LLDRuleCreateResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

    public LLDRuleUpdateResponse update(LLDRuleUpdateRequest request) throws ZabbixApiException {
        LLDRuleUpdateResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, LLDRuleUpdateResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

    public LLDRuleDeleteResponse delete(LLDRuleDeleteRequest request) throws ZabbixApiException {
        LLDRuleDeleteResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, LLDRuleDeleteResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

    public LLDRuleGetResponse get(LLDRuleGetRequest request) throws ZabbixApiException {
        LLDRuleGetResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, LLDRuleGetResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }
}
