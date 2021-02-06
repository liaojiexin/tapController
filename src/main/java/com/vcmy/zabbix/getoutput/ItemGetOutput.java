package com.vcmy.zabbix.getoutput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @ClassName ItemGetOutput
 * @Description TODO
 * @Author xjq
 * @Date 2018/5/8 17:00
 * @Version 1.0
 **/
public class ItemGetOutput extends OutputBase<ItemGetOutput.ITEM_OUTPUT>{

    public static enum  ITEM_OUTPUT{
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
        @SerializedName("status")
        STATUS("status"),
        @SerializedName("lastns")
        LASTNS("lastns"),
        @SerializedName("lastvalue")
        LASTVALUE("lastvalue"),
        @SerializedName("prevvalue")
        PREVVALUE("prevvalue");

        private String value;
        private ITEM_OUTPUT(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        ItemGetOutput output = new ItemGetOutput();
        ArrayList<ITEM_OUTPUT> itemOutputs = new ArrayList<>();
        itemOutputs.add(ITEM_OUTPUT.ITEMID);
        itemOutputs.add(ITEM_OUTPUT.SNMP_OID);
        itemOutputs.add(ITEM_OUTPUT.NAME);
        itemOutputs.add(ITEM_OUTPUT.HOSTID);
        itemOutputs.add(ITEM_OUTPUT.DELAY);
        itemOutputs.add(ITEM_OUTPUT.UNITS);
        itemOutputs.add(ITEM_OUTPUT.LASTCLOCK);
        itemOutputs.add(ITEM_OUTPUT.PREVVALUE);
        itemOutputs.add(ITEM_OUTPUT.LASTVALUE);
        output.setOutputFields(itemOutputs);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ItemGetOutput.class,new OutputBase())
                .create();
        System.out.println(gson.toJson(output));
    }
}
