package com.vcmy.exception;

/**
 * @ClassName: BussinessException.java
 * @Description:  业务异常 550
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月24日 下午7:13:12
 */
public class BussinessException extends RuntimeException{

	private static final long serialVersionUID = -3416266046389335324L;
	
	private final int code;
	
	private final String uri;

	public BussinessException(int code,String msg,String uri) {
		super(msg);
		this.code = code;
		this.uri = uri;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
	
}
