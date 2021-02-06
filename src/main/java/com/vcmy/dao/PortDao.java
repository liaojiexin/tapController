package com.vcmy.dao;

import com.vcmy.entity.OutPortGroup;
import com.vcmy.entity.Port;
import com.vcmy.entity.PortMonitor;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortDao {
    int insert(Port record);

    int insertSelective(Port record);

    List<Port> selectPort(Port port);

    Port selectPortById(Integer portId);

    int updatePort(Port port);

    int selectOutPortCount(@Param("outPortId") Integer outPortId, @Param("strategyId") Integer strategyId);

    int selectInPortCount(@Param("inPortId")Integer inPortId,@Param("strategyId") Integer strategyId);

    String selectPortNameById(@Param("portId")Integer portId);

    Integer selectIdByName(@Param("inport") String inport);

    void insertStrategyInPort(@Param("strategyId") Integer strategyId,@Param("portId") Integer portId);

    void insertStrategyOutPort(@Param("outPortGroupId") Integer outPortGroupId,@Param("portId") Integer portId);

    void deleteInPort(@Param("inPortId") Integer inPortId,@Param("strategyId") Integer strategyId);

    void deleteOutPort(@Param("outPortId") Integer outPortId,@Param("outPortGroupId") Integer outPortGroupId);

    int selectPortType(Port inPort);

    List<Port> selectPortMonitor(Port port);
}