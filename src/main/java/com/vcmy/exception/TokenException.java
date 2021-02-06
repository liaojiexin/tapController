package com.vcmy.exception;

/**
 * 
 * @ClassName: TokenException.java
 * @Description: tocken异常
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:04:28
 */
public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenException() {
		super();
	}

	public TokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenException(String message) {
		super(message);
	}

	public TokenException(Throwable cause) {
		super(cause);
	}

}
