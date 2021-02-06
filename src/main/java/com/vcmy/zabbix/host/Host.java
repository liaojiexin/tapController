/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Suguru Yajima
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vcmy.zabbix.host;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.ZabbixApiMethod;
import com.vcmy.zabbix.getoutput.HostGetOutput;
import com.vcmy.zabbix.getoutput.OutputBase;
import com.vcmy.zabbix.getsearch.HostGetSearch;
import com.vcmy.zabbix.getsearch.SearchBase;
import com.vcmy.zabbix.getselector.SelectInterfaces;
import com.vcmy.zabbix.getselector.SelectorBase;

/**
 * Created by Suguru Yajima on 2014/05/01.
 */
public class Host extends ZabbixApiMethod {

    public Host(String apiUrl, String auth) {
        super(apiUrl, auth);
    }

    public HostCreateResponse create(HostCreateRequest request) throws ZabbixApiException {
        HostCreateResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, HostCreateResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

    public HostUpdateResponse update(HostUpdateRequest request) throws ZabbixApiException {
        HostUpdateResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, HostUpdateResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

    public HostGetResponse get(HostGetRequest request) throws ZabbixApiException {
        HostGetResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SelectInterfaces.class,new SelectorBase())
                .registerTypeAdapter(HostGetOutput.class,new OutputBase())
                .registerTypeAdapter(HostGetSearch.class,new SearchBase())
                .create();
       
        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, HostGetResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

    public HostExistResponse exist(HostExistRequest request) throws ZabbixApiException {
        HostExistResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, HostExistResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }

    public HostDeleteResponse delete(HostDeleteRequest request) throws ZabbixApiException {
        HostDeleteResponse response = null;
        request.setAuth(auth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestJson = gson.toJson(request);

        try {
            String responseJson = sendRequest(requestJson);

            response = gson.fromJson(responseJson, HostDeleteResponse.class);
        } catch (ZabbixApiException e) {
            throw new ZabbixApiException(e);
        }

        return response;
    }
}