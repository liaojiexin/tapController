package com.vcmy.dao;

import com.vcmy.entity.FlowMonitor;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FlowMonitorDao {
    int insert(FlowMonitor record);

    int insertSelective(FlowMonitor record);

    List<FlowMonitor> selectAllFlow(@Param("START") Date START,@Param("END") Date END);
}