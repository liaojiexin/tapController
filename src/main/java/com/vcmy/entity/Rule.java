package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author liaojiexin
 * @Description 一个出端口组有多个规则
 * @Date 2020/12/31 9:33
 * @Param
 * @return
 **/
@ApiModel("策略规则")
public class Rule implements Cloneable{
    @ApiModelProperty("规则id")
    private Integer ruleId;
    @ApiModelProperty("VLAN范围")
    private Integer vlanRange;
    @ApiModelProperty("源mac地址")
    private String sourceMac;
    @ApiModelProperty("目的mac地址")
    private String destinationMac;
    @ApiModelProperty("MPLS标签")
    private Integer mplsLabel;
    @ApiModelProperty("MPLS tc")
    private Integer mplsTc;
    @ApiModelProperty("源IP")
    private String sourceIp;
    @ApiModelProperty("目的IP")
    private String destinationIp;
    @ApiModelProperty("协议")
    private String protocol;
    @ApiModelProperty("源端口")
    private Integer sourcePort;
    @ApiModelProperty("目的端口")
    private Integer destinationPort;
    @ApiModelProperty("TCP flag")
    private String tcpFlag;
    @ApiModelProperty("VLAN动作")
    private String vlanAction;
    @ApiModelProperty("VLAN Id")
    private Integer vlanId;
    @ApiModelProperty("MPLS动作")
    private String mplsAction;
    @ApiModelProperty("动作MPLS标签")
    private Integer mplsActionLabel;
    @ApiModelProperty("修改源IP地址")
    private String updateSourceIp;
    @ApiModelProperty("修改目的IP地址")
    private String updateDestinationIp;
    @ApiModelProperty("修改源MAC地址")
    private String updateSourceMac;
    @ApiModelProperty("修改目的MAC地址")
    private String updateDestinationMac;
    @ApiModelProperty("报文截短")
    private Integer messageTruncate;
    @ApiModelProperty("出端口组")
    private OutPortGroup outPortGroup;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getVlanRange() {
        return vlanRange;
    }

    public void setVlanRange(Integer vlanRange) {
        this.vlanRange = vlanRange;
    }

    public String getSourceMac() {
        return sourceMac;
    }

    public void setSourceMac(String sourceMac) {
        this.sourceMac = sourceMac == null ? null : sourceMac.trim();
    }

    public String getDestinationMac() {
        return destinationMac;
    }

    public void setDestinationMac(String destinationMac) {
        this.destinationMac = destinationMac == null ? null : destinationMac.trim();
    }

    public Integer getMplsLabel() {
        return mplsLabel;
    }

    public void setMplsLabel(Integer mplsLabel) {
        this.mplsLabel = mplsLabel;
    }

    public Integer getMplsTc() {
        return mplsTc;
    }

    public void setMplsTc(Integer mplsTc) {
        this.mplsTc = mplsTc;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp == null ? null : sourceIp.trim();
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp == null ? null : destinationIp.trim();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    public Integer getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    public Integer getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(Integer destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getTcpFlag() {
        return tcpFlag;
    }

    public void setTcpFlag(String tcpFlag) {
        this.tcpFlag = tcpFlag == null ? null : tcpFlag.trim();
    }

    public String getVlanAction() {
        return vlanAction;
    }

    public void setVlanAction(String vlanAction) {
        this.vlanAction = vlanAction == null ? null : vlanAction.trim();
    }

    public Integer getVlanId() {
        return vlanId;
    }

    public void setVlanId(Integer vlanId) {
        this.vlanId = vlanId;
    }

    public String getMplsAction() {
        return mplsAction;
    }

    public void setMplsAction(String mplsAction) {
        this.mplsAction = mplsAction == null ? null : mplsAction.trim();
    }

    public Integer getMplsActionLabel() {
        return mplsActionLabel;
    }

    public void setMplsActionLabel(Integer mplsActionLabel) {
        this.mplsActionLabel = mplsActionLabel;
    }

    public String getUpdateSourceIp() {
        return updateSourceIp;
    }

    public void setUpdateSourceIp(String updateSourceIp) {
        this.updateSourceIp = updateSourceIp == null ? null : updateSourceIp.trim();
    }

    public String getUpdateDestinationIp() {
        return updateDestinationIp;
    }

    public void setUpdateDestinationIp(String updateDestinationIp) {
        this.updateDestinationIp = updateDestinationIp == null ? null : updateDestinationIp.trim();
    }

    public String getUpdateSourceMac() {
        return updateSourceMac;
    }

    public void setUpdateSourceMac(String updateSourceMac) {
        this.updateSourceMac = updateSourceMac == null ? null : updateSourceMac.trim();
    }

    public String getUpdateDestinationMac() {
        return updateDestinationMac;
    }

    public void setUpdateDestinationMac(String updateDestinationMac) {
        this.updateDestinationMac = updateDestinationMac == null ? null : updateDestinationMac.trim();
    }

    public Integer getMessageTruncate() {
        return messageTruncate;
    }

    public void setMessageTruncate(Integer messageTruncate) {
        this.messageTruncate = messageTruncate;
    }

    public OutPortGroup getOutPortGroup() {
        return outPortGroup;
    }

    public void setOutPortGroup(OutPortGroup outPortGroup) {
        this.outPortGroup = outPortGroup;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Rule)super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Rule rule=(Rule) obj;
        if (vlanRange!=null && !vlanRange.equals(rule.getVlanRange()))
            return false;
        if (!StringUtils.isBlank(sourceMac) && !sourceMac.equals(rule.getSourceMac()))
            return false;
        if (!StringUtils.isBlank(destinationMac) && !destinationMac.equals(rule.getDestinationMac()))
            return false;
        if (mplsLabel!=null && mplsLabel!=rule.getMplsLabel())
            return false;
        if (mplsTc!=null && mplsTc!=rule.getMplsTc())
            return false;
        if (!StringUtils.isBlank(sourceIp) && !sourceIp.equals(rule.getSourceIp()))
            return false;
        if (!StringUtils.isBlank(destinationIp) && !destinationIp.equals(rule.getDestinationIp()))
            return false;
        if (!StringUtils.isBlank(protocol) && !protocol.equals(rule.getProtocol()))
            return false;
        if (sourcePort!=null && sourcePort!=rule.getSourcePort())
            return false;
        if (destinationPort!=null && destinationPort!=rule.getDestinationPort())
            return false;
        if (!StringUtils.isBlank(tcpFlag) && !tcpFlag.equals(rule.getTcpFlag()))
            return false;
        return true;
    }
}