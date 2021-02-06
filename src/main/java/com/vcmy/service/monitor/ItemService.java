package com.vcmy.service.monitor;

import com.vcmy.zabbix.ZabbixApiException;
import java.util.Map;

public interface ItemService {

    Integer SelectItemId(String key) throws ZabbixApiException;
}
