package com.vcmy.exception;

/**
 * @ClassName: XmlTransateException.java
 * @Description:  该类的功能描述
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月11日 上午11:59:28
 */
public class XmlTransateException extends RuntimeException{

	private static final long serialVersionUID = -7695794603589456767L;

	public XmlTransateException() {
		super();
	}

	public XmlTransateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public XmlTransateException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlTransateException(String message) {
		super(message);
	}

	public XmlTransateException(Throwable cause) {
		super(cause);
	}
	
}
