package com.vcmy.zabbix.discoveryrule;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DRuleGetObject
 * @Description TODO
 * @Author xjq
 * @Date 2018/6/12 10:51
 * @Version 1.0
 **/
@Data
public class DRuleGetObject {
    private Integer druleid;
    private String iprange;
    private String name;
    private String delay;
    private Long nextcheck;
    private Integer proxy_hostid;
    private Integer status;
    protected List<DCheck> dchecks;
}
