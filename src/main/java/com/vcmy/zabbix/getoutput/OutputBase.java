package com.vcmy.zabbix.getoutput;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @ClassName OutputBase
 * @Description 查询输出基类
 * @Author xjq
 * @Date 2018/5/8 14:08
 * @Version 1.0
 **/
@Data
public class OutputBase <T> implements JsonSerializer<OutputBase> {
    private String output = "extend";
    private List<T> outputFields;

    @Override
    public JsonElement serialize(OutputBase o, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonParser jsonParser = new JsonParser();
        if(o.getOutputFields() != null && o.getOutputFields().size() > 0){
            //System.out.println(o.getOutputFields());
            return jsonParser.parse(o.getOutputFields().toString().toLowerCase());
        }
        else
        //System.out.println(o.getOutput());
        return jsonParser.parse(o.getOutput());
    }
}
