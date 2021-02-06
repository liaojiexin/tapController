package com.vcmy.controller;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vcmy.entity.Message;
import com.vcmy.entity.PageDomain;
import com.vcmy.entity.RestResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BaseController.java
 * @Description: controller分页相关基本方法
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月17日 下午6:55:19
 */
public class BaseController {

	/**
	 * 设置请求分页数据
	 */
	protected void setPageInfo(HttpServletRequest request,Object obj) {
		PageDomain page = (PageDomain) obj;
		analyzeRequest(request,page);
		if (page.getPage()!=null && page.getCount()!=null) {//angularJs
			PageHelper.startPage(page.getPage(), page.getCount(), page.getOrderBy());
		}
	}
	/**
	 * @Title: analyzeRequest 
	 * @Description: 解析请求参数中的Filter 和  sort
	 * @param request HttpServletRequest
	 * @param page PageDomain
	 */
	private void analyzeRequest(HttpServletRequest request, PageDomain page) {
		String searchValue = request.getParameter("searchValue");
		if(StringUtils.isNotBlank(searchValue)){
			try {
				page.setSearchValue(URLDecoder.decode(searchValue,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.getStackTrace();
			}
		}
		//解析sort
		Map<String, String[]> paramMap = request.getParameterMap();
		for(Map.Entry<String, String[]> entity:paramMap.entrySet()){
			String key = entity.getKey();
			if(key.startsWith("sorting")){
				int start = key.indexOf('[');
				int end = key.indexOf(']');
				page.setSort(key.substring(start+1, end));
				page.setIsAsc(entity.getValue()[0]);
				break;
			}
		}
		
	}

	/**
	 * 响应请求分页数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Message getDataMessage(List<?> list) {
		return Message.ok((int)new PageInfo(list).getTotal(),list);
	}
	
	/**
	 * 响应请求分页数据
	 * @param
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Message responseMessage( int listSize,List<?> list) {
		return Message.ok(listSize,list);
	}


	/**
	 * @Author liaojiexin
	 * @Description 前端传字符串自动转化为Date类型
	 * @Date 2020/12/15 21:06
	 * @Param [binder]
	 * @return void
	 **/
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
