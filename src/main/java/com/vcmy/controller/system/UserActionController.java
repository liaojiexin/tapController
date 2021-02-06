package com.vcmy.controller.system;

import com.vcmy.annotation.Log;
import com.vcmy.controller.BaseController;
import com.vcmy.entity.ExcelData;
import com.vcmy.entity.Message;
import com.vcmy.entity.UserAction;
import com.vcmy.service.system.UserActionService;
import com.vcmy.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserActionController
 * @Description: TODO   系统日志
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2020/12/15 15:21
 */
@Api(tags = "系统-系统日志相关接口")
@RestController
public class UserActionController extends BaseController {

    @Autowired
    private UserActionService userActionService;

    /**
     * @Author liaojiexin
     * @Description 查询所有日志
     * @Date 2020/12/15 15:33
     * @Param []
     * @return com.vcmy.entity.Message
     **/
    @ApiOperation("查看日志")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "开始时间",name = "startDate"),
            @ApiImplicitParam(value = "结束时间",name = "endDate")
    })
    @RequestMapping(value = "/tapapi/system/selectlog",method = RequestMethod.GET)
    public Message selectLog(UserAction userAction,Long startDate,Long endDate, HttpServletRequest request){
        setPageInfo(request,userAction);
        List<UserAction> list=userActionService.selectLog(userAction,startDate,endDate);
        return getDataMessage(list);
    }

    /**
     * @Author liaojiexin
     * @Description 导出系统日志
     * @Date 2020/12/15 22:12
     * @Param [businessFlowAll, response]
     * @return void
     **/
    @ApiOperation("导出系统日志")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "开始时间",name = "startDate"),
            @ApiImplicitParam(value = "结束时间",name = "endDate")
    })
    @Log(module = "系统日志",action = "导出系统日志")
    @RequestMapping(value = "/tapapi/system/logexport", method = RequestMethod.POST)
    public void excel(@RequestBody UserAction userAction, @RequestParam("startDate") Long startDate,
                      @RequestParam("endDate") Long endDate, HttpServletResponse response) throws Exception {
        //查询出要导出的信息
        List<UserAction> userActionList=userActionService.selectLog(userAction,startDate,endDate);


        ExcelData data = new ExcelData();
        data.setName("信息数据");
        // 添加表头
        List<String> titles = new ArrayList();
        // for(String title: excelInfo.getNames())
        titles.add("用户名");
        titles.add("时间");
        titles.add("IP地址");
        titles.add("模块");
        titles.add("动作");
        titles.add("结果");
        titles.add("日志");
        titles.add("原因");
        data.setTitles(titles);
        // 添加列
        List<List<Object>> rows = new ArrayList();
        List<Object> row = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < userActionList.size(); i++) {
            row = new ArrayList();
            row.add(userActionList.get(i).getName());
            row.add(df.format(userActionList.get(i).getTime()));
            row.add(userActionList.get(i).getIP());
            row.add(userActionList.get(i).getModule());
            row.add(userActionList.get(i).getAction());
            row.add(userActionList.get(i).getResult());
            row.add(userActionList.get(i).getLog());
            row.add(userActionList.get(i).getReason());
            rows.add(row);

        }

        data.setRows(rows);

        SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = fdate.format(new Date()) + ".xls";
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
