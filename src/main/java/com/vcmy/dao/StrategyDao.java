package com.vcmy.dao;

import com.vcmy.entity.Strategy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StrategyDao {
    int deleteByPrimaryKey(Integer strategyId);

    int insert(Strategy record);

    int insertSelective(Strategy record);

    Strategy selectByPrimaryKey(Integer strategyId);

    int updateByPrimaryKeySelective(Strategy record);

    int updateByPrimaryKey(Strategy record);

    List<Strategy> selectStrategy(Strategy strategy);

    Integer selectMaxid();
}