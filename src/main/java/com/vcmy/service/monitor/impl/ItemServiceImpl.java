package com.vcmy.service.monitor.impl;

import com.vcmy.service.monitor.ItemService;
import com.vcmy.zabbix.ZabbixApiBase;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.getoutput.ItemGetOutput;
import com.vcmy.zabbix.getsearch.ItemGetSearch;
import com.vcmy.zabbix.getsearch.SearchBase;
import com.vcmy.zabbix.item.ItemGetRequest;
import com.vcmy.zabbix.item.ItemGetResponse;
import com.vcmy.zabbix.item.ItemObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ItemService
 * @Description: TODO   监控项
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2021/1/14 15:11
 */
@Service
public class ItemServiceImpl extends ZabbixApiBase implements ItemService {

    /**
     * 根据键值查找监控项id
     * @param key
     * @return
     */
    @Override
    public Integer SelectItemId(String key) throws ZabbixApiException {
        //请求
        ItemGetRequest request=new ItemGetRequest();
        //参数
        ItemGetRequest.Params params=request.getParams();
        //设置监控项要输出的字段
        ItemGetOutput output = new ItemGetOutput();
        ArrayList<ItemGetOutput.ITEM_OUTPUT> itemOutputs = new ArrayList<>();
        itemOutputs.add(ItemGetOutput.ITEM_OUTPUT.ITEMID);
        itemOutputs.add(ItemGetOutput.ITEM_OUTPUT.KEY_);
        output.setOutputFields(itemOutputs);
        params.setOutput(output);
        //设置Search的参数
        ItemGetSearch search = new ItemGetSearch();
        Map<ItemGetSearch.ITEM_SEARCH,List<String>> searchListMap = new HashMap<>();
        List<String> values = new ArrayList<>();
        values.add(key);
        searchListMap.put(ItemGetSearch.ITEM_SEARCH.KEY_,values);
        search.setSearchList(searchListMap);
        params.setSearch(search);
        //发送请求
        ItemGetResponse response = zabbixApi.item().get(request);
        //处理数据
        //数据处理
        Integer itemId=response.getResult().get(0).getItemid(); //监控项id
        return itemId;
    }
}
