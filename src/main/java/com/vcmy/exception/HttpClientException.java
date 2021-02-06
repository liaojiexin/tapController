package com.vcmy.exception;

/**
 * @ClassName: HttpClientException.java
 * @Description:  http请求异常
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月12日 上午11:39:07
 */
public class HttpClientException extends RuntimeException {

	private static final long serialVersionUID = 6688408796114310715L;
	
	public HttpClientException() {
		super();
	}

	public HttpClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HttpClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpClientException(String message) {
		super(message);
	}

	public HttpClientException(Throwable cause) {
		super(cause);
	}

}