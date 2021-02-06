package com.vcmy.controller.strategy;

import com.alibaba.fastjson.JSONObject;
import com.vcmy.annotation.Log;
import com.vcmy.common.Device;
import com.vcmy.common.serivce.SSHConfig;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.Message;
import com.vcmy.entity.Port;
import com.vcmy.entity.PortLine;
import com.vcmy.entity.config.ConfigInterface;
import com.vcmy.service.strategy.PortLineService;
import com.vcmy.service.strategy.PortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: StrategyController
 * @Description: TODO   策略
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/21 15:25
 */
@Api(tags = "策略-接口配置相关接口")
@RestController
public class PortController extends BaseController {

    @Autowired
    private Device device;

    @Autowired
    private PortService portService;

    @Autowired
    private SSHConfig sshConfig;

    @Autowired
    private PortLineService portLineService;

    /**
     * @Author liaojiexin
     * @Description 查看端口
     * @Date 2020/12/24 15:09
     * @Param [port]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("查出所有的端口")
    @RequestMapping(value = "/tapapi/strategy/selectport",method = RequestMethod.GET)
    public Message selectPort(Port port){
        List<Port> list=portService.selectPort(port);
        return getDataMessage(list);
    }

    /**
     * @Author liaojiexin
     * @Description 修改端口
     * @Date 2020/12/24 15:09
     * @Param [port]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("修改端口")
    @Log(module = "策略-端口配置",action = "修改端口")
    @RequestMapping(value = "/tapapi/strategy/updateport",method = RequestMethod.POST)
    public Message updatePort(@RequestBody Port port){
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        String result= sshConfig.updatePort(configInterface,port).substring(2);
        if(result.split("\r\n").length==1)
        {
            if(portService.updatePort(port)==1)
                return Message.ok();
            else
                return Message.error();
        }
        return Message.error();
    }

    /**
     * @Author liaojiexin
     * @Description 查找所有端口连线信息
     * @Date 2021/1/6 11:31
     * @Param
     * @return
     **/
    @ApiOperation("查找所有端口连线信息")
    @RequestMapping(value = "/tapapi/strategy/selectPortLine",method = RequestMethod.GET)
    public Message selectPortLine(PortLine portLine){
        List<PortLine> list;
        if (!JSONObject.toJSONString(portLine).equals("{}") &&
                portLine.getPortIds()!=null &&
                portLine.getPortIds().size()>0){
            list=portLineService.selectPortLine(portLine);
        }
        else {
            list=null;
        }
        return Message.ok(list);
    }
}
