package com.vcmy.dao;

import com.vcmy.entity.Rule;

public interface RuleDao {

    Rule selectByPrimaryKey(Integer ruleId);

    int insertSelective(Rule record);

    int deleteByPrimaryKey(Integer ruleId);

    int updateByPrimaryKeySelective(Rule record);

/*    int insert(Rule record);

    int updateByPrimaryKey(Rule record);

    Integer selectMaxId();*/
}