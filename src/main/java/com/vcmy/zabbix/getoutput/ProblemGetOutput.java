package com.vcmy.zabbix.getoutput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.vcmy.zabbix.problem.ProblemGetRequest;

import java.util.ArrayList;

/**
 * @ClassName ProblemGetOutput
 * @Description TODO
 * @Author xjq
 * @Date 2018/5/8 14:16
 * @Version 1.0
 **/
public class ProblemGetOutput extends OutputBase<ProblemGetOutput.PROBLEM_OUTPUT>{

    public static enum  PROBLEM_OUTPUT{
        
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
       
        @SerializedName("r_clock")
        R_CLOCK("r_clock"),
        @SerializedName("r_ns")
        R_NS("r_ns"),
        @SerializedName("correlationid")
        CORRELATIONID("correlationid"),
        @SerializedName("userid")
        USERID("userid"),
        @SerializedName("acknowledges")
        ACKNOWLEDGES("acknowledges"),
        @SerializedName("tags")
        TAGS("tags");
        
   

        private String value;
        private PROBLEM_OUTPUT(String value){ this.value = value;}
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args){
      
        ProblemGetOutput output = new ProblemGetOutput();
        ArrayList<PROBLEM_OUTPUT> hostout = new ArrayList<>();
        hostout.add(PROBLEM_OUTPUT.CLOCK);
        hostout.add(PROBLEM_OUTPUT.OBJECTID);
        output.setOutputFields(hostout);
        ProblemGetRequest request = new ProblemGetRequest();
        ProblemGetRequest.Params params = request.getParams();
 
        params.setOutput(output);
        Gson g1 = new GsonBuilder()
                .registerTypeAdapter(ProblemGetOutput.class,new OutputBase())
                .create();
        System.out.println(g1.toJson(params));
    }
}
