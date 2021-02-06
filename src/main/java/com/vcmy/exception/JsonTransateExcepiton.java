package com.vcmy.exception;

public class JsonTransateExcepiton extends RuntimeException {

	private static final long serialVersionUID = 5892041378509569473L;

	public JsonTransateExcepiton() {
		super();
	}

	public JsonTransateExcepiton(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JsonTransateExcepiton(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonTransateExcepiton(String message) {
		super(message);
	}

	public JsonTransateExcepiton(Throwable cause) {
		super(cause);
	}

}
