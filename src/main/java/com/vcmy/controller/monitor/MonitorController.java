package com.vcmy.controller.monitor;

import com.vcmy.controller.BaseController;
import com.vcmy.entity.FlowMonitor;
import com.vcmy.entity.Message;
import com.vcmy.entity.PortMonitor;
import com.vcmy.service.monitor.FlowMonitorService;
import com.vcmy.service.monitor.PortMonitorService;
import com.vcmy.zabbix.ZabbixApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName: MonitorController
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/28 13:48
 */
@Api(tags = "流量监控相关接口")
@RestController
public class MonitorController extends BaseController {

    @Autowired
    private FlowMonitorService flowMonitorService;

    @Autowired
    private PortMonitorService portMonitorService;

    /**
     * @Author liaojiexin
     * @Description 查询设备的流量信息，传参开始时间和结束时间（不传值默认为近一个小时）
     * @Date 2020/12/28 14:09
     * @Param [startDate, endDate]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("tap设备的流量监控")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "开始时间",name = "startDate"),
            @ApiImplicitParam(value = "结束时间",name = "endDate")
    })
    @RequestMapping(value = "/tapapi/monitor/tapAllFlow",method = RequestMethod.GET)
    public Message tapAllFlow(Long startDate, Long endDate) throws ZabbixApiException, ExecutionException, InterruptedException {
        Map<String,Object> map=flowMonitorService.tapAllFlow(startDate,endDate);
        return Message.ok(map);
    }

    /**
     * @Author liaojiexin
     * @Description 流量监控-端口流量
     * @Date 2020/12/28 14:27
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("端口 流量监控")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "开始时间",name = "startDate"),
            @ApiImplicitParam(value = "结束时间",name = "endDate")
    })
    @RequestMapping(value = "/tapapi/monitor/portFlow",method = RequestMethod.GET)
    public Message portFlow(PortMonitor portMonitor,Long startDate, Long endDate,HttpServletRequest request) throws ZabbixApiException, ExecutionException, InterruptedException {
        setPageInfo(request,portMonitor);
        Map<String,Object> map=portMonitorService.portFlow(portMonitor,startDate,endDate);
        return Message.ok(map.size(),map);
    }
}
