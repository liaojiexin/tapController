package com.vcmy.controller.alarm;

import com.vcmy.controller.BaseController;
import com.vcmy.entity.Alarm;
import com.vcmy.entity.Message;
import com.vcmy.service.alarm.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AlarmController
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/28 13:58
 */
@Api(tags = "告警相关接口")
@RestController
public class AlarmController extends BaseController {

    @Autowired
    private AlarmService alarmService;

    /**
     * @Author liaojiexin
     * @Description 告警概览，告警信息
     * @Date 2020/12/28 15:45
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("告警概览-告警数量、告警级别占比")
    @RequestMapping(value = "/tapapi/alarm/alarmInfo",method = RequestMethod.GET)
    public Message alarmInfo(){
        Map map =new HashMap();
        Integer pendAlarm=alarmService.selectPendAlarm();   //待处理告警
        map.put("pendAlarm",pendAlarm);
        Integer countAlarm=alarmService.selectCountAlarm(); //告警数量
        map.put("countAlarm",countAlarm);
        Float urgency=alarmService.selectUrgency();  //紧急百分比
        map.put("urgency",urgency);
        Float important=alarmService.selectpImportant();  //重要百分比
        map.put("important",important);
        Float minor=alarmService.selectMinor();  //次要百分比
        map.put("minor",minor);
        Float prompt=alarmService.selectPrompt();  //提示百分比
        map.put("prompt",prompt);
        return Message.ok(map);
    }

    /**
     * @Author liaojiexin
     * @Description 告警所有信息
     * @Date 2020/12/28 15:46
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("告警概览、告警记录-告警所有信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "开始时间",name = "startDate"),
            @ApiImplicitParam(value = "结束时间",name = "endDate")
    })
    @RequestMapping(value = "/tapapi/alarm/alarmAll",method = RequestMethod.GET)
    public Message alarmAll(Alarm alarm,Long startDate, Long endDate, HttpServletRequest request){
        setPageInfo(request,alarm);
        List<Alarm> list=alarmService.alarmAll(alarm,startDate,endDate);
        return getDataMessage(list);
    }

    /**
     * @Author liaojiexin
     * @Description 清除告警记录
     * @Date 2020/12/28 21:48
     * @Param
     * @return
     **/
/*    @ApiOperation("清除告警记录")
    @ApiImplicitParam(value = "告警id",name = "alarmId", dataType = "Integer")
    @RequestMapping(value = "/tapapi/alarm/deleteAlarm",method = RequestMethod.DELETE)
    public Message deleteAlarm(@RequestParam Integer alarmId){
        if(alarmService.deleteAlarm(alarmId)==1)
            return Message.ok();
        else
            return Message.error();
    }*/
}
