package com.vcmy.controller.overview;

import com.vcmy.common.Device;
import com.vcmy.common.serivce.SSHConfig;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.Message;
import com.vcmy.entity.config.ConfigInterface;
import com.vcmy.util.JsonUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: OverviewController
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/18 10:31
 */
@Api(tags = "总览相关接口")
@RestController
public class OverviewController extends BaseController {

    @Autowired
    private SSHConfig sshConfig;

    @Autowired
    private Device device;


    /**
     * @Author liaojiexin
     * @Description 查看系统信息和状态
     * @Date 2020/12/24 ch
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("系统信息和状态")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "内存使用率",name = "MemoryUsage"),
            @ApiImplicitParam(value = "CPU使用率",name = "CpuUsage"),
            @ApiImplicitParam(value = "评分（优、中、良、差）",name = "grade"),
            @ApiImplicitParam(value = "版本",name = "Version"),
            @ApiImplicitParam(value = "序列号",name = "HardwareS/N"),
            @ApiImplicitParam(value = "主机名",name = "host_name")
    })
    @RequestMapping(value = "/tapapi/overview/sysInfo",method = RequestMethod.GET)
    public Message sysInfo(){
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        String sysinfo= sshConfig.sysInfo(configInterface).substring(2);

        //数据处理
        String[] array=sysinfo.split("\r\n");
        Map<String,Object> map=new HashMap<>();
        for (int i=0;i<array.length;i++){
            String info=array[i].replace(" ",""); //去空
            int flag=info.indexOf(":");
            map.put(info.substring(0,flag),info.substring(flag+1));
        }
        float cpu=Float.parseFloat(map.get("CpuUsage").toString());
        String grade = null;
        if (cpu<=25)
            grade="优";
        if (cpu>25&&cpu<=50)
            grade="中";
        if(cpu>50&&cpu<=75)
            grade="良";
        if(cpu>75&&cpu<=100)
            grade="差";
        map.put("grade",grade);

        if (sysinfo!=null)
            return Message.ok(map);
        else
            return Message.error();
    }

    /**
     * @Author liaojiexin
     * @Description 查看温度，风扇，电源
     * @Date 2020/12/24 16:18
     * @Param
     * @return
     **/
    @ApiOperation("查看温度，风扇，电源")
    @RequestMapping(value = "/tapapi/overview/sysInfoOther",method = RequestMethod.GET)
    public Message sysInfoOther(){
        ConfigInterface configInterface=new ConfigInterface();
                configInterface.setSshIp(device.getIp());
        configInterface.setSshUser(device.getUser());
        configInterface.setSshPassword(device.getPassword());
        configInterface.setSshPort(device.getPort());
        String sysinfo= sshConfig.sysInfoOther(configInterface);
        String[] array=sysinfo.split("\r\n");
        if(array.length<5)
            return Message.error();
        for(int i=1;i<6;i=i+2){
            array[i]=array[i].substring(1,array[i].length()-1);
        }
        List<String> list=new ArrayList<>();
        for(int i=1;i<array.length;i=i+2)
        {
            list.add(array[i]);
        }
        Map wendu=new HashMap();
        Map fengsan=new HashMap();
        Map dianya=new HashMap();
        wendu.put("chip",list.get(0));
        wendu.put("cpu",list.get(1));
        wendu.put("wind",list.get(2));
        fengsan.put("fan1",list.get(3));
        fengsan.put("fan2",list.get(4));
        fengsan.put("fan3",list.get(5));
        dianya.put("DC","0.00V");
        dianya.put("AC",list.get(6));
        Map mapName=new HashMap();
        mapName.put("wendu",wendu);
        mapName.put("fengsan",fengsan);
        mapName.put("dianya",dianya);
        List<Map> listMap=new ArrayList<>();
        listMap.add(mapName);
        return Message.ok(listMap);
    }

}
