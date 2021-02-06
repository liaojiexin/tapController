package com.vcmy.dao;

import com.vcmy.entity.PortMonitor;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PortMonitorDao {
    int insert(PortMonitor record);

    int insertSelective(PortMonitor record);

    List<PortMonitor> selectPortFlow(@Param("portMonitor") PortMonitor portMonitor,@Param("START") Date START,@Param("END") Date END);
}