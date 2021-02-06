package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("设备流量监控")
public class FlowMonitor {
    @ApiModelProperty("时间")
    private Date time;
    @ApiModelProperty("总流量")
    private String totalFlow;
    @ApiModelProperty("每秒接收字节速率")
    private String rxFlow;
    @ApiModelProperty("每秒发送字节速率")
    private String txFlow;
    @ApiModelProperty("每秒接收数据包数")
    private String rxPackage;
    @ApiModelProperty("每秒发送数据包数")
    private String txPackage;
    @ApiModelProperty("每秒丢包率")
    private Float lossPacket;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(String totalFlow) {
        this.totalFlow = totalFlow == null ? null : totalFlow.trim();
    }

    public String getRxFlow() {
        return rxFlow;
    }

    public void setRxFlow(String rxFlow) {
        this.rxFlow = rxFlow == null ? null : rxFlow.trim();
    }

    public String getTxFlow() {
        return txFlow;
    }

    public void setTxFlow(String txFlow) {
        this.txFlow = txFlow == null ? null : txFlow.trim();
    }

    public String getRxpackage() {
        return rxPackage;
    }

    public void setRxPackage(String rxPackage) {
        this.rxPackage = rxPackage == null ? null : rxPackage.trim();
    }

    public String getTxPackage() {
        return txPackage;
    }

    public void setTxPackage(String txPackage) {
        this.txPackage = txPackage == null ? null : txPackage.trim();
    }

    public Float getLossPacket() {
        return lossPacket;
    }

    public void setLossPacket(Float lossPacket) {
        this.lossPacket = lossPacket;
    }
}