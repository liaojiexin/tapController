package com.vcmy.zabbix.action;

import com.vcmy.zabbix.utils.ZbxListUtils;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ActionFilter
 * @Description TODO
 * @Author xjq
 * @Date 2018/6/13 10:27
 * @Version 1.0
 **/
@Data
public class ActionFilter {
    private Integer evaltype = EVALTYPE.AND_OR.value;
    private List<ActionCondition> conditions;
    private String eval_formula;
    private String formula;

    public void addActionCondition(ActionCondition ac) {
        conditions = ZbxListUtils.add(conditions, ac);
    }

    public static enum EVALTYPE {
        AND_OR(0),
        AND(1),
        OR(2),
        CUSTOM_EXPRESSION(3);

        public int value;

        private EVALTYPE(int value) {
            this.value = value;
        }
    }
}
