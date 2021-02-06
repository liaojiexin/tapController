package com.vcmy.service.monitor;

import com.vcmy.entity.FlowMonitor;
import com.vcmy.zabbix.ZabbixApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface FlowMonitorService {
    Map<String,Object> tapAllFlow(Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException;
}
