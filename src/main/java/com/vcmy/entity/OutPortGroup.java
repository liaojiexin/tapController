package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author liaojiexin
 * @Description 一个出端口组对应多个集合，一个出端口组对应多个出端口,多个出端口组对一个策略
 * @Date 2020/12/31 9:59
 * @Param
 * @return
 **/
@ApiModel("出端口id组")
public class OutPortGroup implements Cloneable{
    @ApiModelProperty("出端口id组的id")
    private Integer outPortGroupId;
    @ApiModelProperty("规则集合")
    private List<Rule> ruleList;
    @ApiModelProperty("出端口id集合")
    private List<Port> outPortGroup;
/*    @ApiModelProperty("策略")
    private Strategy strategy;*/

    public Integer getOutPortGroupId() {
        return outPortGroupId;
    }

    public void setOutPortGroupId(Integer outPortGroupId) {
        this.outPortGroupId = outPortGroupId;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public List<Port> getOutPortGroup() {
        return outPortGroup;
    }

    public void setOutPortGroup(List<Port> outPortGroup) {
        this.outPortGroup = outPortGroup;
    }

/*    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }*/

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}