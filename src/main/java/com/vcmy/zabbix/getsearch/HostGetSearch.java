package com.vcmy.zabbix.getsearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName HostGetSearch
 * @Description TODO
 * @Author xjq
 * @Date 2018/5/21 16:41
 * @Version 1.0
 **/
public class HostGetSearch extends SearchBase<HostGetSearch.HOST_SEARCH>{
    public static enum  HOST_SEARCH{
        @SerializedName("ip")
        IP("ip"),
        @SerializedName("hostids")
        HOSTIDS("hostids");

        private String value;
        private HOST_SEARCH(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        HostGetSearch search = new HostGetSearch();

        Map<HOST_SEARCH,List<String>> searchListMap = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add("1.1.1.1");
        values.add("2.2.2.2");
        searchListMap.put(HOST_SEARCH.IP,values);
        search.setSearchList(searchListMap);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(HostGetSearch.class,new SearchBase())
                .create();
        System.out.println(gson.toJson(search));
    }
}
