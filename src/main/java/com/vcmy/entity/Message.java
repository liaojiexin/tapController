package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: Message.java
 * @Description:  操作消息提醒
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:02:43
 */
@ApiModel("响应")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

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
	@ApiModelProperty("count")
	private Integer count;
	@ApiModelProperty("data")
	private Object list;

	public Message() {
	}

	public Message(Integer code, String msg, Integer count, Object list) {
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.list = list;
	}

	public static Message ok() {
		return ok("操作成功",null,null);
	}

/*	public static Message ok(String msg) {
		return ok(msg,null,null);
	}*/

	public static Message ok(Object list){
		return ok("操作成功",null,list);
	}

	public static Message ok(String msg,Integer code){
		return new Message(code,msg,null,null);
	}

	public static Message ok(Integer count,Object list){
		return ok("操作成功",count,list);
	}

	public static Message ok( String msg, Integer count,Object list ){
		return new Message(CODE_SUCCESS,msg,count,list);
	}

	public static Message error(){
		return error(CODE_ERROR, "操作失败");
	}

	public static Message error(String msg) {
		return error(CODE_ERROR, msg);
	}


	public static Message error(int code, String msg) {
		return new Message(code,msg, null, (Object)null);
	}

	public static  Message error(ExceptionEnum exceptionEnum){
		Message message = new Message();
		message.setCode(exceptionEnum.getCode());
		message.setMsg(exceptionEnum.getMsg());
		message.setList(null);
		message.setCount(null);
		return  message;
	}

	@Override
	public String toString() {
		return "Message{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", count=" + count +
				", list=" + list +
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Object getList() {
		return list;
	}

	public void setList(Object list) {
		this.list = list;
	}
}
