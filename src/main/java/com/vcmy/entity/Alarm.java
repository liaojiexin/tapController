package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("告警")
public class Alarm extends PageDomain{
    @ApiModelProperty("告警id")
    private Integer alarmId;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("级别（未定义、信息、提示、次要、重要、紧急）")
    private String rank;
    @ApiModelProperty("触发时间")
    private Date time;
    @ApiModelProperty("已持续时间")
    private Integer day;
    @ApiModelProperty("告警状态（已处理、未处理）")
    private String state;

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? null : rank.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}