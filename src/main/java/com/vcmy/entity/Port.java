package com.vcmy.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ApiModel("端口")
public class Port {
    @ApiModelProperty("端口号")
    private Integer portId;
    @ApiModelProperty("端口名称")
    private String portName;
    @ApiModelProperty("端口状态 打开up，关闭down")
    private String state;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("端口类型（入端口0、出端口1、未使用-1）")
    private Integer type;
    @ApiModelProperty("接收包数")
    private String inPacket;
    @ApiModelProperty("发送包数")
    private String outPacket;
    @ApiModelProperty("接收速率")
    private String inRate;
    @ApiModelProperty("发送速率")
    private String outRate;
    @ApiModelProperty("速率")
    private String rate;
    @ApiModelProperty("线路：当该端口为出端口时，连接该端口的入端口名称集合")
    List<String> portNameList;
    @ApiModelProperty("sflow定义的编号")
    private Integer ifIndex;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInPacket() {
        return inPacket;
    }

    public void setInPacket(String inPacket) {
        this.inPacket = inPacket == null ? null : inPacket.trim();
    }

    public String getOutPacket() {
        return outPacket;
    }

    public void setOutPacket(String outPacket) {
        this.outPacket = outPacket == null ? null : outPacket.trim();
    }

    public String getInRate() {
        return inRate;
    }

    public void setInRate(String inRate) {
        this.inRate = inRate == null ? null : inRate.trim();
    }

    public String getOutRate() {
        return outRate;
    }

    public void setOutRate(String outRate) {
        this.outRate = outRate == null ? null : outRate.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
    }

    public List<String> getPortNameList() {
        return portNameList;
    }

    public void setPortNameList(List<String> portNameList) {
        this.portNameList = portNameList;
    }

    public Integer getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(Integer ifIndex) {
        this.ifIndex = ifIndex;
    }

    /** 重写equals方法，实现list.remove（Object）*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Port port=(Port) obj;
        if (portId!=null && portId!=port.getPortId())
            return false;
        if (!StringUtils.isBlank(portName) && !portName.equals(port.getPortName()))
            return false;
        if (!StringUtils.isBlank(state) && !state.equals(port.getState()))
            return false;
        if (!StringUtils.isBlank(description) && !description.equals(port.getDescription()))
            return false;
        if (type!=null && type!=port.getType())
            return false;
        if (!StringUtils.isBlank(inPacket) && !inPacket.equals(port.getInPacket()))
            return false;
        if (!StringUtils.isBlank(outPacket) && !outPacket.equals(port.getOutPacket()))
            return false;
        if (!StringUtils.isBlank(inRate) && !inRate.equals(port.getInRate()))
            return false;
        if (!StringUtils.isBlank(outRate) && !outRate.equals(port.getOutRate()))
            return false;
        if (!StringUtils.isBlank(rate) && !rate.equals(port.getRate()))
            return false;
        return true;
    }
}