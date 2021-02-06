package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName: UserAction
 * @Author: liaojiexin
 * @Description: 功能描述  记录用户的操作
 * @Date: 2020/8/8 13:26
 * @Modified By:
 * @Version: 1.0
 */
@ApiModel("用户操作记录")
public class UserAction extends PageDomain {
    @ApiModelProperty("主键id")
    private Integer useractionId;
    @ApiModelProperty("用户名")
    private String name;    //用户名
    @ApiModelProperty("时间")
    private Date time;      //时间
    @ApiModelProperty("IP地址")
    private String IP;       //IP地址
    @ApiModelProperty("所属模块")
    private String module;     //所属的模块
    @ApiModelProperty("动作")
    private String action;      //动作
    @ApiModelProperty("结果(成功、失败)")
    private String result;      //结果
    @ApiModelProperty("日志")
    private String log;     //日志
    @ApiModelProperty("原因")
    private String reason;  //原因
    private Date START;
    private Date END;

    public Integer getUseractionId() {
        return useractionId;
    }

    public void setUseractionId(Integer useractionId) {
        this.useractionId = useractionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getSTART() {
        return START;
    }

    public void setSTART(Date START) {
        this.START = START;
    }

    public Date getEND() {
        return END;
    }

    public void setEND(Date END) {
        this.END = END;
    }
}
