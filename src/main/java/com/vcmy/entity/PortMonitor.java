package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("端口流量监控")
public class PortMonitor extends PageDomain{
    @ApiModelProperty("端口id")
    private Integer portId;
    @ApiModelProperty("端口名称")
    private String portName;
    @ApiModelProperty("该端口每秒发送字节速率")
    private String txFlow;
    @ApiModelProperty("该端口每秒接收字节速率")
    private String rxFlow;
    @ApiModelProperty("时间")
    private Date time;

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName == null ? null : portName.trim();
    }

    public String getTxFlow() {
        return txFlow;
    }

    public void setTxFlow(String txFlow) {
        this.txFlow = txFlow == null ? null : txFlow.trim();
    }

    public String getRxFlow() {
        return rxFlow;
    }

    public void setRxFlow(String rxFlow) {
        this.rxFlow = rxFlow == null ? null : rxFlow.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}