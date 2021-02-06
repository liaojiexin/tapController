package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author liaojiexin
 * @Description 一条策略有一个入端口组（里面有多个入端口），有多个出端口组（每个出端口组里面有多个出端口），每个出端口组有多条规则
 * @Date 2020/12/31 9:27
 * @Param
 * @return
 **/
@ApiModel("策略")
public class Strategy extends PageDomain implements Cloneable{
    @ApiModelProperty("策略id")
    private Integer strategyId;
    @ApiModelProperty("流量统计")
    private String flowStatistics;
    @ApiModelProperty("入端口组")
    private List<Port> inPortGroup;
    @ApiModelProperty("出端口组的集合")
    private List<OutPortGroup> outPortGroupList;

    public Strategy() {
    }

    public Strategy(Integer strategyId, List<Port> inPortGroup, List<OutPortGroup> outPortGroupList, String flowStatistics) {
        this.strategyId = strategyId;
        this.inPortGroup = inPortGroup;
        this.outPortGroupList = outPortGroupList;
        this.flowStatistics = flowStatistics;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public List<Port> getInPortGroup() {
        return inPortGroup;
    }

    public void setInPortGroup(List<Port> inPortGroup) {
        this.inPortGroup = inPortGroup;
    }

    public List<OutPortGroup> getOutPortGroupList() {
        return outPortGroupList;
    }

    public void setOutPortGroupList(List<OutPortGroup> outPortGroupList) {
        this.outPortGroupList = outPortGroupList;
    }

    public String getFlowStatistics() {
        return flowStatistics;
    }

    public void setFlowStatistics(String flowStatistics) {
        this.flowStatistics = flowStatistics;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Strategy strategy = new Strategy(strategyId, inPortGroup , outPortGroupList, flowStatistics);
        return strategy;
    }
}