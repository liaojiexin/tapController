package com.vcmy.service.monitor.impl;

import com.vcmy.common.Device;
import com.vcmy.dao.FlowMonitorDao;
import com.vcmy.service.monitor.FlowMonitorService;
import com.vcmy.service.monitor.ItemService;
import com.vcmy.util.TimeUtils;
import com.vcmy.zabbix.ZabbixApiBase;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.history.History;
import com.vcmy.zabbix.history.HistoryGetRequest;
import com.vcmy.zabbix.history.HistoryGetResponse;
import com.vcmy.zabbix.history.HistoryObject;
import com.vcmy.zabbix.trend.TrendGetRequest;
import com.vcmy.zabbix.trend.TrendGetResponse;
import com.vcmy.zabbix.trend.TrendObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @ClassName: FlowMonitorServiceImpl
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/28 13:55
 */
@Service
public class FlowMonitorServiceImpl extends ZabbixApiBase implements FlowMonitorService {

    @Autowired
    private FlowMonitorDao flowMonitorDao;

    @Autowired
    private Device device;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ItemService itemService;

    @Override
    public Map<String,Object> tapAllFlow(Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException {
        if(startDate==null)
            startDate=new Date().getTime()/1000-60*60;      //默认一个小时前
        if (endDate==null)
            endDate=new Date().getTime()/1000;
        Long finalStartDate = startDate;
        Long finalEndDate = endDate;
        List<Future<String>> futures = new ArrayList<>();
        Map<String,Object> map=new ConcurrentSkipListMap<>();
        Future<String> future1 = taskExecutor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            if((finalEndDate-finalStartDate)<(24*60 * 60+60)) {     //时间间隔为1天内，使用历史查询
                map.put("Flow", Flow(finalStartDate, finalEndDate));       //设备每秒发送/接收字节速率
            }else {
                map.put("Flow", FlowTrend(finalStartDate, finalEndDate));       //设备每秒发送/接收字节速率
            }
            return threadName;
        });
        futures.add(future1);
        Future<String> future2 = taskExecutor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            if((finalEndDate-finalStartDate)<(24*60 * 60+60)) {     //时间间隔为1天内，使用历史查询
                map.put("Package", Package(finalStartDate, finalEndDate));     //设备每秒发送/接收数据包数
            }else {
                map.put("Package", PackageTrend(finalStartDate, finalEndDate));     //设备每秒发送/接收数据包数
            }
            return threadName;
        });
        futures.add(future2);
        Future<String> future3 = taskExecutor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            if((finalEndDate-finalStartDate)<(24*60 * 60+60)) {     //时间间隔为1天内，使用历史查询
                map.put("DropPackage", DropPackage(finalStartDate, finalEndDate));     //设备发送/接收时丢包数
            }else {
                map.put("DropPackage", DropPackageTrend(finalStartDate, finalEndDate));     //设备发送/接收时丢包数
            }
            return threadName;
        });
        futures.add(future3);
        //如果没有这个循环，主线程和主线程就是独立的   https://www.cnblogs.com/zhangzonghua/p/12878245.html
        for(Future<String> future : futures) {
            System.out.println("主线程输出：" + future.get());
        }
        return map;
    }

    public Map<String,Object> Flow(Long startDate, Long endDate) throws ZabbixApiException {
        Map<String,Object> map=new ConcurrentSkipListMap<>();
        //每秒接收字节速率
        String key1="net.if.rxall.bps";
        Integer itemId1=itemService.SelectItemId(key1) ;  //监控项id
        List<Integer> itemidsList1=new ArrayList<>();
        itemidsList1.add(itemId1);
        //请求
        HistoryGetRequest request1=new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params1=request1.getParams();
        params1.setTime_from(startDate);     //开始时间
        params1.setTime_till(endDate);       //结束时间
        params1.setItemids(itemidsList1);     //监控项id
        params1.setHistory(0);
        params1.setSortField("clock");
        params1.setSortorder("ASC");
        //发送请求
        HistoryGetResponse response1 = zabbixApi.history().get(request1);

        //每秒发送字节速率
        String key2="net.if.txall.bps";
        Integer itemId2=itemService.SelectItemId(key2);  //监控项id
        List<Integer> itemidsList2=new ArrayList<>();
        itemidsList2.add(itemId2);
        //请求
        HistoryGetRequest request2=new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params2=request2.getParams();
        params2.setTime_from(startDate);     //开始时间
        params2.setTime_till(endDate);       //结束时间
        params2.setItemids(itemidsList2);     //监控项id
        params2.setHistory(0);
        params2.setSortField("clock");
        params2.setSortorder("ASC");
        //发送请求
        HistoryGetResponse response2 = zabbixApi.history().get(request2);

        //数据处理
        Integer flag;
        if ((endDate-startDate)>60*60){     //大于一小时
            flag=(response1.getResult().size())/24;
        }else {     //等于小于1小时
            flag=1;
        }
        for(int i=0;i<response1.getResult().size();i=flag+i) {
            HashMap<String,Object> mapAll=new HashMap();
            HistoryObject historyObject1=response1.getResult().get(i);
            SimpleDateFormat format =  new SimpleDateFormat( "MM-dd HH:mm" );
            String times = format.format(new Date(historyObject1.getClock() * 1000L));
            Float value1=Float.parseFloat(historyObject1.getValue())/1024;
            mapAll.put("rxFlow",value1);
            if (response2.getResult().size()> i){
                HistoryObject historyObject2=response2.getResult().get(i);
                Float value2=Float.parseFloat(historyObject2.getValue())/1024;
                mapAll.put("txFlow",value2);
            }
            map.put(times,mapAll);
        }
        return map;
    }

    public Map<String,Object> Package(Long startDate, Long endDate) throws ZabbixApiException {
        Map<String,Object> map=new ConcurrentSkipListMap<>();
        //每秒接收数据包数
        String key1="net.if.rxall.pps";
        Integer itemId1=itemService.SelectItemId(key1);  //监控项id
        List<Integer> itemidsList1=new ArrayList<>();
        itemidsList1.add(itemId1);
        //请求
        HistoryGetRequest request1=new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params1=request1.getParams();
        params1.setTime_from(startDate);     //开始时间
        params1.setTime_till(endDate);       //结束时间
        params1.setItemids(itemidsList1);     //监控项id
        params1.setSortField("clock");
        params1.setSortorder("ASC");
        //发送请求
        HistoryGetResponse response1 = zabbixApi.history().get(request1);

        //每秒发送数据包数
        String key2="net.if.txall.pps";
        Integer itemId2=itemService.SelectItemId(key2) ;  //监控项id
        List<Integer> itemidsList2=new ArrayList<>();
        itemidsList2.add(itemId2);
        //请求
        HistoryGetRequest request2=new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params2=request2.getParams();
        params2.setTime_from(startDate);     //开始时间
        params2.setTime_till(endDate);       //结束时间
        params2.setItemids(itemidsList2);     //监控项id
        params2.setSortField("clock");
        params2.setSortorder("ASC");
        //发送请求
        HistoryGetResponse response2 = zabbixApi.history().get(request2);

        //数据处理
        Integer flag;
        if ((endDate-startDate)>60*60){     //大于一小时
            flag=(response1.getResult().size())/24;
        }else {     //等于小于1小时
            flag=1;
        }
        for(int i=0;i<response1.getResult().size();i=flag+i){
            HashMap<String,Object> mapAll=new HashMap();
            HistoryObject historyObject1=response1.getResult().get(i);
            SimpleDateFormat format =  new SimpleDateFormat( "MM-dd HH:mm" );
            String times = format.format(new Date(historyObject1.getClock() * 1000L));
            Float value1=Float.parseFloat(historyObject1.getValue());
            mapAll.put("rxPackage",value1);
            if (response2.getResult().size()> i){
                HistoryObject historyObject2=response2.getResult().get(i);
                Float value2=Float.parseFloat(historyObject2.getValue());
                mapAll.put("txPackage",value2);
            }
            map.put(times,mapAll);
        }
        return map;
    }

    public Map<String,Object> DropPackage(Long startDate, Long endDate) throws ZabbixApiException {
        Map<String,Object> map=new ConcurrentSkipListMap<>();

        //每秒接收丢包数
        String key1="net.if.rxall.drop";
        Integer itemId1=itemService.SelectItemId(key1);  //监控项id
        List<Integer> itemidsList1=new ArrayList<>();
        itemidsList1.add(itemId1);
        //请求
        HistoryGetRequest request1=new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params1=request1.getParams();
        params1.setTime_from(startDate);     //开始时间
        params1.setTime_till(endDate);       //结束时间
        params1.setItemids(itemidsList1);     //监控项id
        params1.setSortField("clock");
        params1.setSortorder("ASC");
        //发送请求
        HistoryGetResponse response1 = zabbixApi.history().get(request1);

        //每秒发送丢包数
        String key2="net.if.txall.drop";
        Integer itemId2=itemService.SelectItemId(key2);  //监控项id
        List<Integer> itemidsList2=new ArrayList<>();
        itemidsList2.add(itemId2);
        //请求
        HistoryGetRequest request2=new HistoryGetRequest();
        //参数
        HistoryGetRequest.Params params2=request2.getParams();
        params2.setTime_from(startDate);     //开始时间
        params2.setTime_till(endDate);       //结束时间
        params2.setItemids(itemidsList2);     //监控项id
        params2.setSortField("clock");
        params2.setSortorder("ASC");
        //发送请求
        HistoryGetResponse response2 = zabbixApi.history().get(request2);

        //处理数据
        Integer flag;
        if ((endDate-startDate)>60*60){     //大于一小时
            flag=(response1.getResult().size())/24;
        }else {     //等于小于1小时
            flag=1;
        }
        for(int i=0;i<response1.getResult().size();i=flag+i){
            HashMap<String,Object> mapAll=new HashMap();
            HistoryObject historyObject1=response1.getResult().get(i);
            SimpleDateFormat format =  new SimpleDateFormat( "MM-dd HH:mm" );
            String times = format.format(new Date(historyObject1.getClock() * 1000L));
            Float value1=Float.parseFloat(historyObject1.getValue());
            mapAll.put("rxDropPackage",value1);
            if (response2.getResult().size()> i){
                HistoryObject historyObject2=response2.getResult().get(i);
                Float value2=Float.parseFloat(historyObject2.getValue());
                mapAll.put("txDropPackage",value2);
            }
            map.put(times,mapAll);
        }
        return map;
    }

    private Map<String,Object> FlowTrend(Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException {
        Map<String, Object> map = new ConcurrentSkipListMap<>();
        //每秒接收字节速率
        String key1 = "net.if.rxall.bps";
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
        String key2="net.if.txall.bps";
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

    private Map<String,Object> PackageTrend(Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException {
        Map<String, Object> map = new ConcurrentSkipListMap<>();
        //每秒接收字节速率
        String key1 = "net.if.rxall.pps";
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
        String key2="net.if.txall.pps";
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
            mapAll.put("rxPackage", value1);
            if (response2.getResult().size() > i) {
//                TrendObject trendObject2 = response2.getResult().get(i);
//                Float value2 = Float.parseFloat(trendObject2.getValue_avg()) / 1024;
                Float value2 = 0f;
                for (int j=i;j<((flag+i)>response2.getResult().size()?response2.getResult().size():(flag+i));j++){
                    TrendObject trendObject2 = response2.getResult().get(j);
                    value2=value2+Float.parseFloat(trendObject2.getValue_avg()) / 1024;
                }
                value2=value2/flag;
                mapAll.put("txPackage", value2);
            }
            map.put(times, mapAll);
        }
        return map;
    }

    private Map<String,Object> DropPackageTrend(Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException {
        Map<String, Object> map = new ConcurrentSkipListMap<>();
        //每秒接收字节速率
        String key1 = "net.if.rxall.drop";
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
        String key2="net.if.txall.drop";
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
            mapAll.put("rxDropPackage", value1);
            if (response2.getResult().size() > i) {
//                TrendObject trendObject2 = response2.getResult().get(i);
//                Float value2 = Float.parseFloat(trendObject2.getValue_avg()) / 1024;
                Float value2 = 0f;
                for (int j=i;j<((flag+i)>response2.getResult().size()?response2.getResult().size():(flag+i));j++){
                    TrendObject trendObject2 = response2.getResult().get(j);
                    value2=value2+Float.parseFloat(trendObject2.getValue_avg()) / 1024;
                }
                value2=value2/flag;
                mapAll.put("txDropPackage", value2);
            }
            map.put(times, mapAll);
        }
        return map;
    }
}
