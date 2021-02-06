package com.vcmy.zabbix.action;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ActionGetObject
 * @Description TODO
 * @Author xjq
 * @Date 2018/6/13 9:54
 * @Version 1.0
 **/
@Data
public class ActionGetObject extends ActionObject {
    private ActionFilter filter;
    private List<ActionOperation> operations;
}
