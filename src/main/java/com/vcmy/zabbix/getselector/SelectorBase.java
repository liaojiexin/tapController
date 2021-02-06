package com.vcmy.zabbix.getselector;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @ClassName SelectorBase
 * @Description 查询选择器基类
 * @Author xjq
 * @Date 2018/5/8 13:38
 * @Version 1.0
 **/
@Data
public class SelectorBase<T> implements JsonSerializer<SelectorBase> {
    private String selectString = "extend";
    private List<T> selector;

    @Override
    public JsonElement serialize(SelectorBase t, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonParser jsonParser = new JsonParser();
        if(t.getSelector()!= null && t.getSelector().size() > 0){
            //System.out.println(t.getSelector());
            return jsonParser.parse(t.getSelector().toString().toLowerCase());
        }
        //System.out.println(t.getSelectString());
        return jsonParser.parse(t.getSelectString());
    }
}
