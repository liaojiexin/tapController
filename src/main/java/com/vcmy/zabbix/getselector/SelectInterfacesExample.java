package com.vcmy.zabbix.getselector;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SelectInterfaces
 * @Description Host.get API中的Interfaces选择器
 * @Author xjq
 * @Date 2018/5/8 10:51
 * @Version 1.0
 **/
@Data
public class SelectInterfacesExample implements JsonSerializer<SelectInterfacesExample> {
    private String selectInterface;
    private List<SELECT_INTERFACE> selectInterfaces;

    @Override
    public JsonElement serialize(SelectInterfacesExample selectInterfaces, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonParser jsonParser = new JsonParser();
        if(selectInterfaces.getSelectInterface() != null && !"".equals(selectInterfaces.getSelectInterface())){
            return jsonParser.parse(selectInterfaces.getSelectInterface());
        }
        return jsonParser.parse(selectInterfaces.getSelectInterfaces().toString().toLowerCase());
    }

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
        SelectInterfacesExample sn1 = new SelectInterfacesExample();
        SelectInterfacesExample sn2 = new SelectInterfacesExample();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        sn1.setSelectInterface("extend");
        //System.out.println(gson.toJson(sn1));
        ArrayList<SELECT_INTERFACE> si = new ArrayList<>();
        si.add(SELECT_INTERFACE.HOSTID);
        si.add(SELECT_INTERFACE.PORT);
        sn2.setSelectInterfaces(si);
        System.out.println(gson.toJson(sn2));
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(SelectInterfacesExample.class, new SelectInterfacesExample());
        Gson g1 = gb.create();
        //System.out.println(g1.toJson(sn1));
        //System.out.println(g1.toJson(sn2));
        //HostGetRequest request = new HostGetRequest();
        //HostGetRequest.Params params = request.getParams();
        //params.setSelectInterfaces(sn1);
        //params.setOutput("extend");
        //System.out.println(g1.toJson(params));
    }
}
