package com.vcmy.exception;

/**
 * 
 * @ClassName: PermissionException.java
 * @Description:  权限异常
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:43:35
 */
public class PermissionException extends RuntimeException{

	private static final long serialVersionUID = 3477175583850546417L;

	public PermissionException() {
		super();
	}

	public PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionException(String message) {
		super(message);
	}

	public PermissionException(Throwable cause) {
		super(cause);
	}
	
}
