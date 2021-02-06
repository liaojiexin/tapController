package com.vcmy.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.vcmy.interceptor.handler.SessionIdInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public SessionIdInterceptor sessionIdInterceptor(){
		return new SessionIdInterceptor();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
/*		registry.addViewController("/sdnapi/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);*/
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")// 设置允许跨域的路径
				.allowedOrigins("*")// 设置允许跨域请求的域名
				.allowCredentials(true)// 是否允许证书 不再默认开启
				.allowedMethods("*")// 设置允许的方法
				.allowedHeaders("*");

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/upload/**").addResourceLocations("file:img/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {     //注册拦截器
//		registry.addInterceptor(sessionIdInterceptor())
//				.excludePathPatterns("/sdnapi/index.html","/sdnapi/","/sdnapi/system/users/logout","/sdnapi/system/users/login","/sdnapi/**/*.js",
//						"/sdnapi/**/*.css","/sdnapi/**/*.png", "/sdnapi/**/*.jpg","/sdnapi/**/*.jpeg", "/sdnapi/**/*.gif", "/sdnapi/**/fonts/*","/sdnapi/**/*.svg"
//						,"/sdnapi/verify","/sdnapi/META-INF/**","/sdnapi/apiMock/auth/logout","/sdnapi/user/logout");

//        registry.addInterceptor(sessionIdInterceptor())
//				.addPathPatterns("/sdnapi/system/menu/**","/sdnapi/system/office/**","/sdnapi/system/role/","/sdnapi/config/**","/sdnapi/lldptopology",
//						"/sdnapi/flowOverview/**","/sdnapi/networkFlow/**","/sdnapi/GroupFlow/**","/sdnapi/deviceFlow/**","/sdnapi/BusinessFlow/**",
//						"/sdnapi/alarm/**","/sdnapi/device/**","/sdnapi/StrategyLink/**","/sdnapi/intelligentSchedule/**","/sdnapi/dispatch/**",
//						"/sdnapi/linkStrategy/**","/sdnapi/security/**","/sdnapi/**/queryAll","/sdnapi/bgp/**","/sdnapi/api/**","/sdnapi/charge/**",
//						"/sdnapi/system/user/**","/system/role","/sdnapi/organize/*");
	}

}