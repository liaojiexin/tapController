package com.vcmy.service.monitor;

import com.vcmy.entity.PortMonitor;
import com.vcmy.zabbix.ZabbixApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface PortMonitorService {

    Map<String,Object> portFlow(PortMonitor portMonitor, Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException;
}
