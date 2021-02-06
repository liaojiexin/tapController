package com.vcmy.controller.system;

import com.vcmy.annotation.Log;
import com.vcmy.common.Device;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.Message;
import com.vcmy.entity.config.ConfigInterface;
import com.vcmy.common.serivce.SSHConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @ClassName: SystemController
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/16 10:37
 */
@Api(tags = "系统-tap设备设置相关接口")
@RestController
public class SystemController extends BaseController {

    @Autowired
    private Device device;

    @Autowired
    private SSHConfig sshConfig;

    /**
     * @Author liaojiexin
     * @Description 重启系统
     * @Date 2020/12/18 15:05
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("重启系统")
    @Log(module = "重启系统",action = "重启系统")
    @RequestMapping(value = "/tapapi/system/reboot",method = RequestMethod.PUT)
    public Message reboot(){
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        String result = sshConfig.reboot(configInterface);
        return Message.ok();
    }

    /**
     * @Author liaojiexin
     * @Description 查询出当前系统时间
     * @Date 2020/12/18 15:05
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("当前系统时间")
    @RequestMapping(value = "/tapapi/system/selecttime",method = RequestMethod.GET)
    public Message selecttime() throws ParseException {
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        String time = sshConfig.selectTime(configInterface).replace("\r\n", "");
        //时间处理
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time.indexOf("UTC")>-1)      //标准时间
            sdf1.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        if(time.indexOf("CST")>-1)      //上海时间
            sdf1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = sdf.parse(time);
        String date1=sdf1.format(date);
        return Message.ok(date1);
    }


    /**
     * @Author liaojiexin
     * @Description 修改系统时间
     * @Date 2020/12/18 17:18
     * @Param [timezone, time]
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("修改系统时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "timezone",value = "时区（世界标准UTC，中国Asia/Shanghai）"),
            @ApiImplicitParam(name = "time",value = "时间（格式yyyy-MM-dd HH:mm:ss）")
    })
    @RequestMapping(value = "/tapapi/system/updatetime",method = RequestMethod.POST)
    public Message update(String timezone,String time){
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        sshConfig.updateTime(configInterface,timezone,time);
        return Message.ok();
    }
}
