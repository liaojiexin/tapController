package com.vcmy.zabbix.getsearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ItemGetSearch
 * @Description TODO
 * @Author xjq
 * @Date 2018/5/15 11:30
 * @Version 1.0
 **/
@Data
public class ItemGetSearch extends SearchBase<ItemGetSearch.ITEM_SEARCH>{

    public static enum  ITEM_SEARCH{
        @SerializedName("itemid")
        ITEMID("itemid"),
        @SerializedName("snmp_oid")
        SNMP_OID("snmp_oid"),
        @SerializedName("hostid")
        HOSTID("hostid"),
        @SerializedName("name")
        NAME("name"),
        @SerializedName("key_")
        KEY_("key_"),
        @SerializedName("delay")
        DELAY("delay"),
        @SerializedName("value_type")
        VALUE_TYPE("value_type"),
        @SerializedName("units")
        UNITS("units"),
        @SerializedName("description")
        DESCRIPTION("description"),
        @SerializedName("lastclock")
        LASTCLOCK("lastclock"),
        @SerializedName("lastns")
        LASTNS("lastns"),
        @SerializedName("lastvalue")
        LASTVALUE("lastvalue"),
        @SerializedName("prevvalue")
        PREVVALUE("prevvalue");

        private String value;
        private ITEM_SEARCH(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        ItemGetSearch search = new ItemGetSearch();
        ArrayList<ITEM_SEARCH> itemSearchs = new ArrayList<>();
        itemSearchs.add(ITEM_SEARCH.SNMP_OID);

        Map<ITEM_SEARCH,List<String>> searchListMap = new HashMap<>();
        List<String> values = new ArrayList<>();
//        values.add(SnmpOidsMap.MIB2SYSTEM.oid);
//        values.add(SnmpOidsMap.MIB2INTERFACES.oid);
//        values.add(SnmpOidsMap.MIB2IPADDRTABLE.oid);
//        values.add(SnmpOidsMap.MIB2IPCIDRROUTETABLE.oid);
        searchListMap.put(ITEM_SEARCH.SNMP_OID,values);
        search.setSearchList(searchListMap);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ItemGetSearch.class,new SearchBase())
                .create();
        System.out.println(gson.toJson(search));
    }
}
