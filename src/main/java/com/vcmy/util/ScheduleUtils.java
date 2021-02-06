package com.vcmy.util;

import com.vcmy.common.Device;
import com.vcmy.common.serivce.SSHConfig;
import com.vcmy.entity.*;
import com.vcmy.entity.config.ConfigInterface;
import com.vcmy.service.alarm.AlarmService;
import com.vcmy.service.monitor.ItemService;
import com.vcmy.service.strategy.PortLineService;
import com.vcmy.service.strategy.PortService;
import com.vcmy.service.strategy.StrategyService;
import com.vcmy.zabbix.ZabbixApiBase;
import com.vcmy.zabbix.ZabbixApiException;
import com.vcmy.zabbix.getoutput.ItemGetOutput;
import com.vcmy.zabbix.getsearch.ItemGetSearch;
import com.vcmy.zabbix.item.ItemGetRequest;
import com.vcmy.zabbix.item.ItemGetResponse;
import com.vcmy.zabbix.item.ItemObject;
import com.vcmy.zabbix.problem.Problem;
import com.vcmy.zabbix.problem.ProblemGetRequest;
import com.vcmy.zabbix.problem.ProblemGetResponse;
import com.vcmy.zabbix.problem.ProblemObject;
import io.swagger.models.auth.In;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: ScheduleUtils
 * @Description: TODO 定时器，定时从设备查出数据存到数据库
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/18 11:00
 */
@Service
public class ScheduleUtils extends ZabbixApiBase {

    @Autowired
    private Device device;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private SSHConfig sshConfig;

    @Autowired
    private PortService portService;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private PortLineService portLineService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AlarmService alarmService;

    /**
     * @Author liaojiexin
     * @Description 查询设备端口和down/up状态并存到数据库
     * @Date 2020/12/18 11:02
     * @Param []
     * @return void
     **/
//    @Scheduled(cron = "0 0/30 * * * ? ")
    public void portSchedule() {
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        //查询所有端口和down/up
        String result= sshConfig.portSchedule(configInterface);

        //数据库处理，按照端口名称来判断数据库表是否有，如果没有就插入新数据，
        // 如果有数据则判断是不是最新数据，不是的话就进行修改
        //数据处理
        int logo=result.indexOf("\rp eth")+6;
        String[] results=result.substring(logo).replace("\"","").substring(2).split("\r\n");
        for(int i=0;i<results.length-1;i++){  //例子：key=eth0 value=down
            int flag=results[i].indexOf(" ");
            String key=results[i].substring(flag+1).replace(" ","");
            String value=results[i].substring(0,flag);
            Port port=new Port();
            port.setPortName(key);
            port.setState(value);
            List<Port> ports=portService.selectPort(port);  //根据端口名称去数据库查询，查看是否有该数据库
            if(ports.size()==0){    //没有该端口名称相关的数据
                port.setType(-1);   //-1端口未使用
                portService.insertSelective(port);  //插入数据
            }else {
                if (ports.get(0).getPortName().equals(key) && !ports.get(0).getState().equals(value)){ //数据库有该端口，但是端口状态已经改变
                    portService.updatePort(port);
                }
            }
        }
    }

    /**
     * @Author liaojiexin
     * @Description 查询端口剩余信息
     * @Date 2020/12/24 13:29
     * @Param
     * @return
     **/
//    @Scheduled(cron = "0 0/30 * * * ? ")
    /*public void portInfoSchedule(){   //直接设备ssh查询
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        List<Port> portList=portService.selectPort(null);    //查出所有端口
        for (Port port:portList) {
            Runnable run = new Runnable(){
                @Override
                public void run() {
                        String result =sshConfig.portInfoSchedule(configInterface,port).substring(2);
                        //信息处理
                        String[] results=result.replace("\"","").split("\r\n");
                        List<String> info=new ArrayList<>();    //端口属性-速率，端口统计-接收包数，端口统计-发送包数，端口统计-接收速率，端口统计-发送速率
                        for(int i=0;i<results.length;i++){
                            if(results[i].indexOf("~#")<0)
                                info.add(results[i]);
                        }

                        if(info.get(0).equals("1Gbps"))  //端口属性-速率
                        {
                            port.setRate("1000");
                        }else if(info.get(0).equals("10Gbps")){
                            port.setRate("10000");
                        }else {
                            port.setRate("0");
                        }
                        port.setInPacket(info.get(1));  //端口统计-接收包数
                        port.setOutPacket(info.get(2));     //端口统计-发送包数
                        port.setInRate(info.get(3).substring(info.get(3).indexOf("=")+1,info.get(3).length()-4));   //端口统计-接收速率
                        port.setOutRate(info.get(4).substring(info.get(4).indexOf("=")+1,info.get(4).length()-4));  //端口统计-发送速率
                        portService.updatePort(port);
                        System.out.println("结束："+System.currentTimeMillis());
                }
            };
            taskExecutor.execute(run);
        }
    }*/
//    @Scheduled(cron = "0 0/30 * * * ? ")
    public void portInfoSchedule(){     //调用zabbix Api
        List<Port> portList=portService.selectPort(null);    //查出所有端口
        for (Port port:portList) {
            Runnable run = new Runnable(){
                @SneakyThrows
                @Override
                public void run() {
                    //请求
                    ItemGetRequest request=new ItemGetRequest();
                    //参数
                    ItemGetRequest.Params params=request.getParams();
                    //设置监控项要输出的字段
                    ItemGetOutput output = new ItemGetOutput();
                    ArrayList<ItemGetOutput.ITEM_OUTPUT> itemOutputs = new ArrayList<>();
                    itemOutputs.add(ItemGetOutput.ITEM_OUTPUT.KEY_);
                    itemOutputs.add(ItemGetOutput.ITEM_OUTPUT.LASTVALUE);
                    output.setOutputFields(itemOutputs);
                    params.setOutput(output);
                    //设置Search的参数
                    ItemGetSearch search = new ItemGetSearch();
                    Map<ItemGetSearch.ITEM_SEARCH,List<String>> searchListMap = new HashMap<>();
                    List<String> values = new ArrayList<>();
                    values.add(port.getIfIndex().toString());
                    searchListMap.put(ItemGetSearch.ITEM_SEARCH.KEY_,values);
                    search.setSearchList(searchListMap);
                    params.setSearch(search);
                    //发送请求
                    ItemGetResponse response = zabbixApi.item().get(request);
                    for (ItemObject itemObject:response.getResult()){
                        if(itemObject.getKey_().equals("net.if.tx.bps["+port.getIfIndex()+"]")){    //	端口每秒发送字节速率
                            Float value=Float.parseFloat(itemObject.getLastvalue())/1024/1024;
                            port.setOutRate(value.toString());
                        }else if (itemObject.getKey_().equals("net.if.rx.bps["+port.getIfIndex()+"]")){     //	端口每秒接收字节速率
                            Float value=Float.parseFloat(itemObject.getLastvalue())/1024/1024;
                            port.setInRate(value.toString());
                        }else if (itemObject.getKey_().equals("net.if.tx.pps["+port.getIfIndex()+"]")){     //	端口每秒发送数据包数
                            port.setOutPacket(itemObject.getLastvalue());
                        }else if (itemObject.getKey_().equals("net.if.rx.pps["+port.getIfIndex()+"]")){     //	端口每秒接收数据包数
                            port.setInPacket(itemObject.getLastvalue());
                        }else if (itemObject.getKey_().equals("net.if.speed["+port.getIfIndex()+"]")){      //	端口速率
                            Integer rate=Integer.parseInt(itemObject.getLastvalue())/(1000*1000);
                            port.setRate(rate.toString());
                        }
                    }
                    portService.updatePort(port);
                }
            };
            taskExecutor.execute(run);
        }
    }

    /**
     * @Author liaojiexin
     * @Description 策略定时器，定时从查找策略流量统计,匹配域+入端口表示策略唯一性,查出连线信息(主要为速率)
     * @Date 2020/12/25 13:35
     * @Param
     * @return
     **/
//    @Scheduled(cron = "0 0/30 * * * ? ")
    public void strategySchedule() throws InterruptedException {
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());

        String result1=sshConfig.strategySchedule(configInterface).substring(2);
        Long time1=new Date().getTime()/1000;
        String[] results1=result1.split("\r\n");
        Thread.sleep(1000);     //1秒

        String result2=sshConfig.strategySchedule(configInterface).substring(2);
        Long time2=new Date().getTime()/1000;
        String[] results2=result2.split("\r\n");
        Long time=time2-time1;

        HashMap<Integer,Long> mapStrategy=new HashMap<>();
        for(int i=0;i<results1.length;i++){
            /** 策略流量统计*/
            int bytes_flag1=results1[i].indexOf("n_bytes=")+8;
            int bytes_flag2=results1[i].indexOf(",",bytes_flag1);
            Long flowStatistics1=Long.valueOf(results1[i].substring(bytes_flag1,bytes_flag2))/(1024*1024);    //该条策略的流量统计
            Long flowStatistics2=Long.valueOf(results2[i].substring(bytes_flag1,bytes_flag2))/(1024*1024);    //该条策略的流量统计

            //编号
            int flag1 =results1[i].indexOf("cookie=0x")+9;
            int flag2 = results1[i].indexOf(",",flag1);
            String cookie16=results1[i].substring(flag1,flag2);
            Integer cookie= Integer.parseInt(cookie16,16);      //十六进制转十进制，对应策略号

            if(mapStrategy.get(cookie)!=null){
                mapStrategy.put(cookie,mapStrategy.get(cookie)+flowStatistics1);
            }else {
                mapStrategy.put(cookie,flowStatistics1);
            }

            /** 端口与端口之间的连线速率*/
            //cookie号为0的策略排除
            if (cookie==0){
                //入端口
                int in_flag1 =results1[i].indexOf("in_port=")+8; //入端口标志位
                int in_flag2;
                if(results1[i].indexOf(" ",in_flag1)<results1[i].indexOf(",",in_flag1)){ //入端口后第一个逗号或第一个空格
                    in_flag2=results1[i].indexOf(" ",in_flag1);        //没有规则的情况
                }else {
                    in_flag2=results1[i].indexOf(",",in_flag1);
                }
                String inport=results1[i].substring(in_flag1,in_flag2);    //入端口
                String inPortId=portService.selectIdByName(inport);     //入端口id组

                //出端口
                List<String> list=new ArrayList<>();   //出端口组
                int out_flag1 =results1[i].indexOf("output:");   //第一个出端口标志位
                if (out_flag1>-1){
                    list=findOutput(list,out_flag1+7,results1[i]);   //迭代找出所有出端口
                }
                String[] strings=new String[list.size()];   //存放出端口的端口id
                for (int j=0;j<list.size();j++) {
                    String outPort=portService.selectIdByName(list.get(j));
                    strings[j]=outPort;
                }

                //数据库操作
                PortLine portLine=new PortLine();
                portLine.setInPortId(Integer.parseInt(inPortId));   //进端口
                portLine.setStrategyId(cookie);     //策略id
                Long flowStatistics=(flowStatistics2-flowStatistics1)/time;
                portLine.setRate(flowStatistics.toString());    //速率
                for (String outPortId:strings){
                    portLine.setOutPortId(Integer.parseInt(outPortId));     //出端口
                    portLineService.updatePortLine(portLine);
                }
            }
        }

        for (Integer key : mapStrategy.keySet()) {      //修改流量
            Strategy strategy=new Strategy();
            strategy.setStrategyId(key);
            strategy.setFlowStatistics(mapStrategy.get(key).toString());
            strategyService.updateStrategyFlowStatistics(strategy);
        }

    }

    public List<String> findOutput(List stringList,int out_flag,String results){
        if(out_flag>-1){
            int flag =results.indexOf("output:",out_flag);
            if (flag>-1){
                findOutput(stringList,flag+7,results);
                String outport=results.substring(out_flag,flag-1);
                stringList.add(outport);
            }else {
                String outport=results.substring(out_flag);
                stringList.add(outport);
            }
        }
        return stringList;
    }


    /**
     * @Author liaojiexin
     * @Description 定时查询一下zabbix中端口的键值对
     * @Date 2021/1/12 9:53
     * @Param
     * @return
     **/
//    @Scheduled(cron = "0 0/30 * * * ? ")
    public void zabbixKeySchedule(){
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        String result =sshConfig.zabbixKeySchedule(configInterface);
        int flag1=result.indexOf("[");
        int flag2=result.indexOf("]");
        result=result.substring(flag1+1,flag2).replace("\r\n","").replace("{","").replace(" ","");
        String[] strings=result.split("}");
        for (String s:strings){
            int flag_portName1=s.indexOf("\"metricValue\":\"")+15;
            int flag_portName2=s.indexOf("\"",flag_portName1);
            String portName=s.substring(flag_portName1,flag_portName2); //端口名称

            int flag_ifIndex1=s.indexOf("\"dataSource\":\"")+14;
            int flag_ifIndex2=s.indexOf("\"",flag_ifIndex1);
            Integer ifIndex=Integer.parseInt(s.substring(flag_ifIndex1,flag_ifIndex2));

            Port port=new Port();
            port.setPortName(portName);
            port.setIfIndex(ifIndex);
            portService.updatePort(port);
        }
    }


    /**
     * @Author liaojiexin
     * @Description 定时器采集告警
     * @Date 2021/1/19 11:06
     * @Param
     * @return
     **/
//    @Scheduled(cron = "0 0/30 * * * ? ")
    public void alertSchedule() throws ZabbixApiException {
        //请求
        ProblemGetRequest request=new ProblemGetRequest();
        //参数
        ProblemGetRequest.Params params=request.getParams();
        List<Integer> hostIds=new ArrayList<>();
        hostIds.add(10272);
        params.setHostids(hostIds);
        params.setSortField("eventid");
        params.setSortorder("ASC");
        //发送请求
        ProblemGetResponse response = zabbixApi.problem().get(request);
        Long nowdate=new Date().getTime()/1000;
        Alarm alarm=new Alarm();
        for (ProblemObject problemObject:response.getResult()){
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    alarm.setAlarmId(Integer.parseInt(problemObject.getEventid()));
                    alarm.setDescription(problemObject.getName());
                    switch (problemObject.getSeverity()) {
                        case 0:
                            alarm.setRank("未定义");
                            break;
                        case 1:
                            alarm.setRank("信息");
                            break;
                        case 2:
                            alarm.setRank("提示");
                            break;
                        case 3:
                            alarm.setRank("次要");
                            break;
                        case 4:
                            alarm.setRank("重要");
                            break;
                        case 5:
                            alarm.setRank("紧急");
                            break;
                    }
                    Date date = TimeUtils.stampToDate(problemObject.getClock());
                    alarm.setTime(date);
                    alarm.setDay((int) ((nowdate - problemObject.getClock()) / (60 * 60 * 24)));
                    if (problemObject.getR_eventid().equals("0"))
                        alarm.setState("未处理");
                    else
                        alarm.setState("已处理");

                    //数据库处理
                    alarmService.insertAlarm(alarm);
                }
            };
            taskExecutor.execute(run);
        }
    }
}
