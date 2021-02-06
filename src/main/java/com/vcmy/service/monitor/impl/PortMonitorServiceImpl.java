package com.vcmy.service.monitor.impl;

import com.vcmy.dao.PortDao;
import com.vcmy.dao.PortMonitorDao;
import com.vcmy.entity.Port;
import com.vcmy.entity.PortMonitor;
import com.vcmy.service.monitor.ItemService;
import com.vcmy.service.monitor.PortMonitorService;
import com.vcmy.zabbix.ZabbixApi;
import com.vcmy.zabbix.ZabbixApiBase;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.getoutput.ItemGetOutput;
import com.vcmy.zabbix.getsearch.ItemGetSearch;
import com.vcmy.zabbix.history.HistoryGetRequest;
import com.vcmy.zabbix.history.HistoryGetResponse;
import com.vcmy.zabbix.history.HistoryObject;
import com.vcmy.zabbix.host.HostGetResponse;
import com.vcmy.zabbix.item.ItemGetRequest;
import com.vcmy.zabbix.item.ItemGetResponse;
import com.vcmy.zabbix.trend.TrendGetRequest;
import com.vcmy.zabbix.trend.TrendGetResponse;
import com.vcmy.zabbix.trend.TrendObject;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * @ClassName: PortMonitorServiceImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/28 13:53
 */
@Service
public class PortMonitorServiceImpl extends ZabbixApiBase implements PortMonitorService {

    @Autowired
    private PortMonitorDao portMonitorDao;

    @Autowired
    private PortDao portDao;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * @Author liaojiexin
     * @Description 考虑到数据量多的问题，默认只差出一个小时的数据，然后前端分页查询，所以不要一次性查出所有端口数据，
     * 然后不要分端口调用zabbix Api，一次性调用全部再进行整理
     * @Date 2021/1/18 11:26
     * @Param [portMonitor, startDate, endDate]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> portFlow(PortMonitor portMonitor, Long startDate, Long endDate) throws ExecutionException, InterruptedException, ZabbixApiException {
        System.out.println("开始：" + new Date().getTime());
        if (startDate == null)
            startDate = new Date().getTime() / 1000 - 60 * 60;      //默认一个小时前
        if (endDate == null)
            endDate = new Date().getTime() / 1000;
        Map<String, Object> map = new ConcurrentSkipListMap();
        Port port1 = new Port();
        if (!StringUtils.isBlank(portMonitor.getPortName()))
            port1.setPortName(portMonitor.getPortName());
        List<Port> list = portDao.selectPortMonitor(port1);
        List<Future<String>> futures = new ArrayList<>();
        for (Port port : list) {
            Long finalStartDate = startDate;
            Long finalEndDate = endDate;
            Future<String> future = taskExecutor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                if((finalEndDate-finalStartDate)<(24*60 * 60+60)){     //时间间隔为1天内，使用历史查询
                    Map<String, Object> map1 = Flow(port.getIfIndex(), finalStartDate, finalEndDate);
                    map.put(port.getPortName(), map1);
                }else {     //1天外使用趋势查询
                    Map<String, Object> map1 = FlowTrend(port.getIfIndex(), finalStartDate, finalEndDate);
                    map.put(port.getPortName(), map1);
                }
                return threadName;
            });
            futures.add(future);
        }
        //如果没有这个循环，主线程和主线程就是独立的   https://www.cnblogs.com/zhangzonghua/p/12878245.html
        for(Future<String> future : futures) {
            System.out.println("主线程输出：" + future.get());
        }
        System.out.println("结束：" + new Date().getTime());
        return map;
    }


    public Map<String, Object> Flow(Integer key, Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException {
        Map<String, Object> map = new ConcurrentSkipListMap<>();
        //每秒接收字节速率
        String key1 = ("\"net.if.rx.bps[" + key + "]\"").trim();
        Integer itemId1 = itemService.SelectItemId(key1);  //监控项id
        List<Integer> itemidsList1 = new ArrayList<>();
        itemidsList1.add(itemId1);
        //请求
        HistoryGetRequest request1 = new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params1 = request1.getParams();
        params1.setTime_from(startDate);     //开始时间
        params1.setTime_till(endDate);       //结束时间
        params1.setItemids(itemidsList1);     //监控项id
        params1.setHistory(0);
        params1.setSortField("clock");
        params1.setSortorder("ASC");
        //发送请求
        Future<HistoryGetResponse> future1 = taskExecutor.submit(() -> {
            HistoryGetResponse response1 = zabbixApi.history().get(request1);
            return response1;
        });

        //每秒发送字节速率
        String key2 = ("\"net.if.tx.bps[" + key + "]\"").trim();
        Integer itemId2 = itemService.SelectItemId(key2);  //监控项id
        List<Integer> itemidsList2 = new ArrayList<>();
        itemidsList2.add(itemId2);
        //请求
        HistoryGetRequest request2 = new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params2 = request2.getParams();
        params2.setTime_from(startDate);     //开始时间
        params2.setTime_till(endDate);       //结束时间
        params2.setItemids(itemidsList2);     //监控项id
        params2.setHistory(0);
        params2.setSortField("clock");
        params2.setSortorder("ASC");
        //发送请求
        Future<HistoryGetResponse> future2 = taskExecutor.submit(() -> {
            HistoryGetResponse response2 = zabbixApi.history().get(request2);
            return response2;
        });

        //数据处理
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        HistoryGetResponse response1=future1.get();
        HistoryGetResponse response2=future2.get();
        Integer flag;
        if ((endDate-startDate)>60*60){     //大于一小时
            flag=(response1.getResult().size())/24;
        }else {     //等于小于1小时
            flag=1;
        }
        for (int i = 0; i < response1.getResult().size(); i=flag+i) {
            HashMap<String, Object> mapAll = new HashMap();
            HistoryObject historyObject1 = response1.getResult().get(i);
            String times = format.format(new Date(historyObject1.getClock() * 1000L));
            Float value1 = Float.parseFloat(historyObject1.getValue()) / 1024;
            mapAll.put("rxFlow", value1);
            if (response2.getResult().size() > i) {
                HistoryObject historyObject2 = response2.getResult().get(i);
                Float value2 = Float.parseFloat(historyObject2.getValue()) / 1024;
                mapAll.put("txFlow", value2);
            }
            map.put(times, mapAll);
        }
        return map;
    }


    public Map<String, Object> FlowTrend(Integer key,Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException {
        Map<String, Object> map = new ConcurrentSkipListMap<>();
        //每秒接收字节速率
        String key1 = ("\"net.if.rx.bps[" + key + "]\"").trim();
        Integer itemId1 = itemService.SelectItemId(key1);  //监控项id
        List<Integer> itemidsList1 = new ArrayList<>();
        itemidsList1.add(itemId1);
        //请求
        TrendGetRequest request1 = new TrendGetRequest();
        //参数
        TrendGetRequest.Params params1 = request1.getParams();
        params1.setTime_from(startDate);     //开始时间
        params1.setTime_till(endDate);       //结束时间
        params1.setItemids(itemidsList1);     //监控项id
        params1.setSortField("clock");
        params1.setSortorder("ASC");
        //发送请求
        Future<TrendGetResponse> future1 = taskExecutor.submit(() -> {
            TrendGetResponse response1 = zabbixApi.trend().get(request1);
            return response1;
        });

        //每秒发送字节速率
        String key2 = ("\"net.if.tx.bps[" + key + "]\"").trim();
        Integer itemId2 = itemService.SelectItemId(key2);  //监控项id
        List<Integer> itemidsList2 = new ArrayList<>();
        itemidsList2.add(itemId2);
        //请求
        TrendGetRequest request2 = new TrendGetRequest();
        //参数
        TrendGetRequest.Params params2 = request2.getParams();
        params2.setTime_from(startDate);     //开始时间
        params2.setTime_till(endDate);       //结束时间
        params2.setItemids(itemidsList2);     //监控项id
        params2.setSortField("clock");
        params2.setSortorder("ASC");
        //发送请求
        Future<TrendGetResponse> future2 = taskExecutor.submit(() -> {
            TrendGetResponse response2 = zabbixApi.trend().get(request2);
            return response2;
        });

        //数据处理
        startDate=startDate/24/60/60*24*60*60-8*60*60;
        Integer time =(int)((endDate-startDate)/(24*60*60))+1;
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        TrendGetResponse response1=future1.get();
        TrendGetResponse response2=future2.get();
        int flag=response1.getResult().size()/time;
        for (int i = 0; i < response1.getResult().size(); i=flag+i) {
            HashMap<String, Object> mapAll = new HashMap();
//            TrendObject trendObject1 = response1.getResult().get(i);
//            String times = format.format(new Date(trendObject1.getClock() * 1000L));
            String times = format.format(new Date(response1.getResult().get(i).getClock() * 1000L));
            Float value1 = 0f;
            for (int j=i;j<((flag+i)>response1.getResult().size()?response1.getResult().size():(flag+i));j++){
                TrendObject trendObject1 = response1.getResult().get(j);
                value1=value1+Float.parseFloat(trendObject1.getValue_avg()) / 1024;
            }
//            value1 = Float.parseFloat(trendObject1.getValue_avg()) / 1024;
            value1=value1/flag;
            mapAll.put("rxFlow", value1);
            if (response2.getResult().size() > i) {
//                TrendObject trendObject2 = response2.getResult().get(i);
//                Float value2 = Float.parseFloat(trendObject2.getValue_avg()) / 1024;
                Float value2 = 0f;
                for (int j=i;j<((flag+i)>response2.getResult().size()?response2.getResult().size():(flag+i));j++){
                    TrendObject trendObject2 = response2.getResult().get(j);
                    value2=value2+Float.parseFloat(trendObject2.getValue_avg()) / 1024;
                }
                value2=value2/flag;
                mapAll.put("txFlow", value2);
            }
            map.put(times, mapAll);
        }
        return map;
    }
}
