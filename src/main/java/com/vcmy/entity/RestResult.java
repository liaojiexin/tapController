//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("响应")
public class RestResult implements Serializable {

    private static final long serialVersionUID = 8676838788771498843L;

    private static final Integer CODE_SUCCESS = 0;

    private static final Integer CODE_ERROR = -1;

    private static final Integer CODE_REDIRECT = 302;

    // 表示用户未登录
    public static final Integer CODE_AUTHENTICATED = 401;

    // 表示用户已登录但不能访问指定资源
    public static final Integer CODE_FORBIDDEN = 403;

    @ApiModelProperty("code")
    private Integer code;
    @ApiModelProperty("msg")
    private String msg;
    @ApiModelProperty("data")
    private Object data;

    private RestResult() {
    }

    private RestResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RestResult buildSuccess() {
        return buildSuccess(null);
    }

    public static RestResult buildSuccess(Object data) {
        return buildSuccess(data, "success");
    }

    public static RestResult buildSuccess(Object data, String msg) {
        return new RestResult(CODE_SUCCESS, msg, data);
    }

    public static RestResult buildError(String msg) {
        return new RestResult(CODE_ERROR, msg, null);
    }

    public static RestResult buildError(Integer code, String msg) {
        return new RestResult(code, msg, null);
    }

    public static RestResult buildError(String msg, Object data) {
        return new RestResult(CODE_ERROR, msg, data);
    }

    public static  RestResult buildError(ExceptionEnum exceptionEnum){
        RestResult restResult = new RestResult();
        restResult.setCode(exceptionEnum.getCode());
        restResult.setMsg(exceptionEnum.getMsg());
        restResult.setData(null);
        return  restResult;
    }

    public static RestResult buildRedirect(String redirectUrl) {
        return new RestResult(CODE_REDIRECT, "redirect page...", redirectUrl);
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
