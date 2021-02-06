package com.vcmy.service.strategy;

import com.vcmy.entity.PortLine;
import com.vcmy.entity.Rule;
import com.vcmy.entity.Strategy;

import java.util.List;
import java.util.Map;

public interface StrategyService {
    List<Strategy> selectStrategy(Strategy strategy);

    String insertStrategy(Strategy strategy) throws CloneNotSupportedException;

    String deleteStrategy(Integer strategyId) throws CloneNotSupportedException;

    String updateStrategy(Strategy strategyNew) throws CloneNotSupportedException;

    Strategy selectByPrimaryKey(Strategy strategy);

    Map<String, Object> checkStrategy(Strategy strategy, String add);

    void updateStrategyFlowStatistics(Strategy strategy);

    String updateRule(Rule rule) throws CloneNotSupportedException;

}
