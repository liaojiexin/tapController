package com.vcmy.zabbix.getselector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.vcmy.zabbix.host.HostGetRequest;

import java.util.ArrayList;

/**
 * @ClassName SelectInterfaces
 * @Description 接口选择类
 * @Author xjq
 * @Date 2018/5/8 13:50
 * @Version 1.0
 **/
public class SelectInterfaces extends SelectorBase<SelectInterfaces.SELECT_INTERFACE>{

    public static enum  SELECT_INTERFACE{
        @SerializedName("interfaceid")
        INTERFACEID("interfaceid"),
        @SerializedName("hostid")
        HOSTID("hostid"),
        MAIN("main"),
        @SerializedName("type")
        TYPE("type"),
        @SerializedName("useip")
        USEIP("useip"),
        @SerializedName("ip")
        IP("ip"),
        @SerializedName("dns")
        DNS("dns"),
        @SerializedName("port")
        PORT("port"),
        @SerializedName("bulk")
        BULK("bulk");
        private String value;
        private SELECT_INTERFACE(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        SelectInterfaces sn1 = new SelectInterfaces();
        SelectInterfaces sn2 = new SelectInterfaces();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        sn1.setSelectString("extend");
        //System.out.println(gson.toJson(sn1));
        ArrayList<SELECT_INTERFACE> si = new ArrayList<>();
        si.add(SelectInterfaces.SELECT_INTERFACE.HOSTID);
        si.add(SelectInterfaces.SELECT_INTERFACE.PORT);
        sn2.setSelector(si);
        System.out.println(gson.toJson(sn2));
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(SelectInterfaces.class,new SelectorBase());
        Gson g1 = gb.create();
        //System.out.println(g1.toJson(sn1));
        //System.out.println(g1.toJson(sn2));
        HostGetRequest request = new HostGetRequest();
        HostGetRequest.Params params = request.getParams();
        params.setSelectInterfaces(sn2);
        //params.setOutput("extend");
        System.out.println(g1.toJson(params));
    }
}
