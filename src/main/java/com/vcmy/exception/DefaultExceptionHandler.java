package com.vcmy.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vcmy.entity.Message;

/**
 * 
 * @ClassName: DefaultExceptionHandler.java
 * @Description: 自定义异常处理器
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:04:13
 */
@RestControllerAdvice
public class DefaultExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	/**
	 * Bad Request
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST) 
	public Message handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		return Message.error(400,e.getMessage());
	}

	/**
	 * sql异常
	 */
	@ExceptionHandler(SQLException.class)  
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  
    public Message handleSQLException(SQLException e) {  
		logger.error("sql:",e);
        return Message.error(500, e.getMessage());
    }  

	/**
	 * token验证
	 */
	@ExceptionHandler(TokenException.class)
//	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseStatus(value = HttpStatus.OK)
	public Message handleTokenException(TokenException e) {
//		return Message.error(401, e.getMessage());
		return Message.error(-1, "token无效或过期");
	}

	/**
	 * permission验证
	 */
	@ExceptionHandler(PermissionException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public Message handlePermissionException(PermissionException e) {
		return Message.error(403, e.getMessage());
	}
	
	/**
	 * Media Type
	 */
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public Message handleHttpMediaTypeNotSupportedException(Exception e) {
		return Message.error(415,e.getMessage());
	}
	
	/**
	 * httpclient请求异常
	 */
	@ExceptionHandler(HttpClientException.class)
	public Message handleHttpClientException(HttpClientException e,HttpServletResponse response) {
		response.setStatus(506);
		return Message.error(506, e.getMessage());
	}
	

	/**
	 * 业务异常
	 */
	@ExceptionHandler(BussinessException.class)
	public Message handleBussinessException(BussinessException e,HttpServletResponse response){
		logger.info("{}:{}:{}",e.getCode(),e.getUri(),e.getMessage());
		response.setStatus(e.getCode());
		return Message.error(e.getCode(), e.getMessage());
	}
	
	/**
	 * 拦截未知的运行时异常
	 */
	@ExceptionHandler(RuntimeException.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseStatus(value = HttpStatus.OK)
	public Message handleRuntime(RuntimeException e) {
		return Message.error(-1, "系统异常，请联系系统维护人员");
//		return Message.error(500, e.getMessage());
	}

	/**
	 * 系统异常
	 */
	@ExceptionHandler(Exception.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseStatus(value = HttpStatus.OK)
	public Message handleException(Exception e) {
		logger.error("error:",e);
		return Message.error(-1, "系统异常，请联系系统维护人员");
//		return Message.error(500, e.getMessage());
	}

}
