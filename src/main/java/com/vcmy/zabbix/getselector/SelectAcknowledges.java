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
public class SelectAcknowledges extends SelectorBase<SelectAcknowledges.SELECT_ACKNOWLEDGES>{

    public static enum  SELECT_ACKNOWLEDGES{
        @SerializedName("acknowledgeid")
        ACKNOWLEDGEID("acknowledgeid"),
        @SerializedName("userid")
        USERID("userid"),
        @SerializedName("eventid")
        EVENID("eventid"),
        @SerializedName("clock")
        CLOCK("clock"),
        @SerializedName("message")
        MESSAGE("message"),
        @SerializedName("action")
        ACTION("action");
        private String value;
        private SELECT_ACKNOWLEDGES(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        SelectAcknowledges sn1 = new SelectAcknowledges();
        SelectAcknowledges sn2 = new SelectAcknowledges();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        sn1.setSelectString("extend");
        //System.out.println(gson.toJson(sn1));
        ArrayList<SELECT_ACKNOWLEDGES> si = new ArrayList<>();
        si.add(SelectAcknowledges.SELECT_ACKNOWLEDGES.CLOCK);
        si.add(SelectAcknowledges.SELECT_ACKNOWLEDGES.MESSAGE);
        sn2.setSelector(si);
        System.out.println(gson.toJson(sn2));
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(SelectAcknowledges.class,new SelectorBase());
        Gson g1 = gb.create();
        //System.out.println(g1.toJson(sn1));
        //System.out.println(g1.toJson(sn2));
        HostGetRequest request = new HostGetRequest();
        HostGetRequest.Params params = request.getParams();
      //  params.setSelectInterfaces(sn2);
        //params.setOutput("extend");
        System.out.println(g1.toJson(params));
    }
}
