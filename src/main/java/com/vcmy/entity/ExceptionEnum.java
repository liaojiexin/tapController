package com.vcmy.entity;

/**
 * @ClassName: ExceptionEnum
 * @Description: TODO
 * @version: 1.0
 * @author: liaojiexin
 * @date: 2021/1/4 18:12
 */
public enum  ExceptionEnum {

    SUCCESS(0, "请求成功"),
    ERROR(-1, "请求失败");

    private int code;
    private String msg;

    ExceptionEnum(int code, String msg) {
        this.code=code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
