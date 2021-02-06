package com.vcmy.zabbix.getoutput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.vcmy.zabbix.event.EventGetRequest;

import java.util.ArrayList;

/**
 * @ClassName EVENTGetOutput
 * @Description TODO
 * @Author xjq
 * @Date 2018/5/8 14:16
 * @Version 1.0
 **/
public class EventGetOutput extends OutputBase<EventGetOutput.EVENT_OUTPUT>{

    public static enum  EVENT_OUTPUT{
        
        @SerializedName("eventid")
        EVENTID("eventid"),
        @SerializedName("source")
        SOURCE("source"),
        @SerializedName("object")
        OBJECT("object"),
        @SerializedName("objectid")
        OBJECTID("objectid"),
        @SerializedName("clock")
        CLOCK("clock"),
        @SerializedName("ns")
        NS("ns"),
        @SerializedName("r_eventid")
        R_EVENTID("r_eventid"),
        @SerializedName("value")
        VALUE("value"),
        @SerializedName("c_eventid")
        C_EVENTID("c_eventid"),
        @SerializedName("acknowledged")
        ACKNOWLEDGED("acknowledged"),
        @SerializedName("correlationid")
        CORRELATIONID("correlationid"),
        @SerializedName("userid")
        USERID("userid"),
        @SerializedName("acknowledges")
        ACKNOWLEDGES("acknowledges"),
        @SerializedName("tags")
        TAGS("tags");
        
   

        private String value;
        private EVENT_OUTPUT(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
      
        EventGetOutput output = new EventGetOutput();
        ArrayList<EVENT_OUTPUT> hostout = new ArrayList<>();
        hostout.add(EVENT_OUTPUT.CLOCK);
        hostout.add(EVENT_OUTPUT.OBJECTID);
        output.setOutputFields(hostout);
        EventGetRequest request = new EventGetRequest();
        EventGetRequest.Params params = request.getParams();
 
        params.setOutput(output);
        Gson g1 = new GsonBuilder()
                .registerTypeAdapter(EventGetOutput.class,new OutputBase())
                .create();
        System.out.println(g1.toJson(params));
    }
}
