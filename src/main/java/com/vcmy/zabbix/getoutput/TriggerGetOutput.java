package com.vcmy.zabbix.getoutput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.vcmy.zabbix.getselector.SelectInterfaces;
import com.vcmy.zabbix.getselector.SelectorBase;
import com.vcmy.zabbix.host.HostGetRequest;

import java.util.ArrayList;

/**
 * @ClassName HostGetOutput
 * @Description TODO
 * @Author xjq
 * @Date 2018/5/8 14:16
 * @Version 1.0
 **/
public class TriggerGetOutput extends OutputBase<TriggerGetOutput.TRIGGER_OUTPUT>{

    public static enum  TRIGGER_OUTPUT{
        @SerializedName("triggerid")
        TRIGGERID("triggerid"),
        @SerializedName("description")
        DESCRIPTION("description"),
        @SerializedName("comments")
        COMMENTS("comments"),
        @SerializedName("error")
        ERROR("error"),
        @SerializedName("expression")
        EXPRESSION("expression"),
        @SerializedName("flags")
        FLAGS("flags"),
        @SerializedName("lastchange")
        LASTCHANGE("lastchange"),
        @SerializedName("priority")
        PRIORITY("priority"),
        @SerializedName("status")
        STATUS("status"),
        @SerializedName("state")
        STATE("state"),
        @SerializedName("templateid")
        TEMPLATEID("templateid"),
        @SerializedName("type")
        TYPE("type"),
        @SerializedName("url")
        URL("url"),
        @SerializedName("value")
        VALUE("value"),
        @SerializedName("recovery_mode")
        RECOVERY_MODE("recovery_mode"),
        @SerializedName("recovery_expression")
        RECOVERY_EXPRESSION("recovery_expression");
       

        private String value;
        private TRIGGER_OUTPUT(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
        SelectInterfaces sn2 = new SelectInterfaces();
    
        TriggerGetOutput output = new TriggerGetOutput();
        ArrayList<TRIGGER_OUTPUT> hostout = new ArrayList<>();
        hostout.add(TRIGGER_OUTPUT.TRIGGERID);
        hostout.add(TRIGGER_OUTPUT.STATUS);
        output.setOutputFields(hostout);
        HostGetRequest request = new HostGetRequest();
        HostGetRequest.Params params = request.getParams();
        params.setSelectInterfaces(sn2);
        params.setOutput(output);
        Gson g1 = new GsonBuilder()
                .registerTypeAdapter(SelectInterfaces.class,new SelectorBase())
                .registerTypeAdapter(TriggerGetOutput.class,new OutputBase())
                .create();
        System.out.println(g1.toJson(params));
    }
}
