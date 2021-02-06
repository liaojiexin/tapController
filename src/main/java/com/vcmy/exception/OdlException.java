package com.vcmy.exception;

/**
 * @ClassName: OdlException.java
 * @Description:  控制器产生的异常
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月24日 下午2:04:12
 */
public class OdlException extends RuntimeException{

	// @Fields serialVersionUID : 
	private static final long serialVersionUID = 1L;
	
	private final int code;
	
	public OdlException(int code, String message){
		super(message);
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

}
