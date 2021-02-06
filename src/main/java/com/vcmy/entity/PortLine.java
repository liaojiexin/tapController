package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.util.List;

@ApiModel("端口连线")
public class PortLine {
    @ApiModelProperty("连线id")
    private Integer lineId;
    @ApiModelProperty("端口id集合")
    private List<Integer> portIds;
    @ApiModelProperty("进端口id")
    private Integer inPortId;
    @ApiModelProperty("进端口")
    private Port inPort;
    @ApiModelProperty("出端口id")
    private Integer outPortId;
    @ApiModelProperty("出端口")
    private Port outPort;
    @ApiModelProperty("速率")
    private String rate;
    @ApiModelProperty("策略id")
    private Integer strategyId;
    @ApiModelProperty("规则id集合")
    private List<Integer> ruleList;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public List<Integer> getPortIds() {
        return portIds;
    }

    public void setPortIds(List<Integer> portIds) {
        this.portIds = portIds;
    }

    public Integer getInPortId() {
        return inPortId;
    }

    public void setInPortId(Integer inPortId) {
        this.inPortId = inPortId;
    }

    public Integer getOutPortId() {
        return outPortId;
    }

    public void setOutPortId(Integer outPortId) {
        this.outPortId = outPortId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public List<Integer> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Integer> ruleList) {
        this.ruleList = ruleList;
    }

    public Port getInPort() {
        return inPort;
    }

    public void setInPort(Port inPort) {
        this.inPort = inPort;
    }

    public Port getOutPort() {
        return outPort;
    }

    public void setOutPort(Port outPort) {
        this.outPort = outPort;
    }
}