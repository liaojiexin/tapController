package com.vcmy.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;



@Component
public class SimpleCORSFilter implements Filter  {


	 @Override
	 public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	    HttpServletResponse httpResponse = (HttpServletResponse) res;
	    // 如果是cookie跨域，则需要设置为ajax请求服务器的ip或者域名，如果只是跨域访问，则设置为 * 即可
	    httpResponse.setHeader("Access-Control-Allow-Origin", "http://10.0.213.10");
	    httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE，OPTIONS");
	    httpResponse.setHeader("Access-Control-Max-Age", "7200");
	    httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
	    chain.doFilter(req, httpResponse);
	}

	@Override
	public void destroy() {
	
	}
}