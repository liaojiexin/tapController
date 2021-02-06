package com.vcmy.zabbix.getsearch;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Data;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SearchBase
 * @Description
 * @Author xjq
 * @Date 2018/5/15 11:21
 * @Version 1.0
 **/
@Data
public class SearchBase<T> implements JsonSerializer<SearchBase> {
    Map<T,List<String>> searchList = new HashMap<>();
    @Override
    public JsonElement serialize(SearchBase searchBase, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonParser jsonParser = new JsonParser();
        if(searchBase.getSearchList() != null && searchBase.getSearchList().size() >0){
            return jsonParser.parse(searchBase.getSearchList().toString().toLowerCase());
        }
        return null;
    }
}
