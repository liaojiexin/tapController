package com.vcmy.controller;

import com.vcmy.entity.Strategy;
import com.vcmy.service.strategy.StrategyService;
import com.vcmy.util.ScheduleUtils;
import com.vcmy.zabbix.ZabbixApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName: TestController
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/21 17:44
 */
@RestController
public class TestController {

    @Autowired
    private ScheduleUtils scheduleUtils;

    @Autowired
    private StrategyService strategyService;

    @RequestMapping(value = "/test/portSchedule",method = RequestMethod.GET)
    public void portScheduleTest(){
        scheduleUtils.portSchedule();
    }

    @RequestMapping(value = "/test/portInfoSchedule",method = RequestMethod.GET)
    public void portInfoSchedule(){
        scheduleUtils.portInfoSchedule();
    }

    @RequestMapping(value = "/test/strategySchedule",method = RequestMethod.GET)
    public void strategySchedule() throws InterruptedException {
        scheduleUtils.strategySchedule();
    }


    @RequestMapping(value = "/test/checkStrategy",method = RequestMethod.POST)
    public void checkStrategy(@RequestBody Strategy strategy){
        Map<String, Object> map= strategyService.checkStrategy(strategy,"update");
        System.out.println(map);
    }

    @RequestMapping(value = "/test/zabbixKeySchedule",method = RequestMethod.GET)
    public void zabbixKeySchedule(){
        scheduleUtils.zabbixKeySchedule();
    }

    @RequestMapping(value = "/test/alertSchedule",method = RequestMethod.GET)
    public void alertSchedule() throws ZabbixApiException {
        scheduleUtils.alertSchedule();
    }
}
