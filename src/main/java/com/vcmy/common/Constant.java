package com.vcmy.common;
/**
 * 
 * @ClassName: Constant.java
 * @Description:  常量
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午10:57:22
 */
public class Constant {
	
	private Constant(){
	}

	/** token名称 */
	public static final String DEFAULT_TOKEN_NAME = "X-Auth-Token";

	/** 正常状态 */
	public static final int NORMAL = 0;

	/** 异常状态 */
	public static final int EXCEPTION = 1;

	/** 通用成功标识 */
	public static final String SUCCESS = "0";

	/** 通用失败标识 */
	public static final String FAIL = "1";
	
	/** 通用占位符*/
	public static final String PLACEHOLDER = "?";
	
	public static final String PLACECHAR = "#";
	public static final String PLACECHARPOINT = ".";
	
	public static final int ERRORSTATUS = 550;
	
	public static final String RESULT = "result";
}
