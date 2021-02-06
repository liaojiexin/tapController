package com.vcmy.dao;

import com.vcmy.entity.PortLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortLineDao {
    int deleteByPrimaryKey(Integer lineId);

    int insert(PortLine record);

    int insertSelective(PortLine record);

    PortLine selectByPrimaryKey(Integer lineId);

    int updateByPrimaryKeySelective(PortLine record);

    int updateByPrimaryKey(PortLine record);

    void updatePortLine(PortLine portLine);

    void insertLineRule(@Param("lineId") Integer lineId,@Param("ruleId") Integer ruleId);

    void deleteLineRule(@Param("ruleId") Integer ruleId);

    void deletePortLine(@Param("inPortId")Integer inPortId,@Param("outPortId") Integer outPortId,@Param("strategyId") Integer strategyId);

    List<PortLine> selectPortLine(PortLine portLine);
}