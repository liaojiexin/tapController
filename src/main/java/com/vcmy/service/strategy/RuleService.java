package com.vcmy.service.strategy;

import com.vcmy.entity.Rule;

public interface RuleService {
    Rule selectRulebyId(Integer ruleId);

    String checkStrategyRule(Rule rule);

}
