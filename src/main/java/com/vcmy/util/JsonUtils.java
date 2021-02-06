package com.vcmy.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vcmy.exception.JsonTransateExcepiton;

/**
 * 
 * @ClassName: JsonUtils.java
 * @Description:  json工具
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午11:05:52
 */
public class JsonUtils {
	
	private JsonUtils(){
		
	}
	
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	}

	/**
	 * Convert object into json
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2json(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new JsonTransateExcepiton("invoke obj2json fail：" + obj, e);
		}
	}

	/**
	 * Convert json into object
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2obj(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new JsonTransateExcepiton("invoke json2obj fail: " + clazz + "," + json, e);
		}
	}

	/**
	 * Convert json into List or Array
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2obj(String json, TypeReference<T> jsonTypeReference) {
		try {
			return objectMapper.readValue(json, jsonTypeReference);
		} catch (Exception e) {
			throw new JsonTransateExcepiton("invoke json2obj fail: " + jsonTypeReference + "," + json, e);
		}
	}
	
	/**
	 * @Title: json2objNoRootName 
	 * @Description: 去除最外层rootName
	 * @param json
	 * @param jsonTypeReference
	 * @return T    返回类型 
	 */
	public static <T> T json2objNoRootName(String json, TypeReference<T> jsonTypeReference) {
		return json2obj(removeRootName(json), jsonTypeReference);
	}
	
	/**
	 * @Title: removeRootName 
	 * @Description: 去除rootName
	 * @param json
	 * @return String    返回类型 
	 */
	public static String removeRootName(String json){
		int index = json.indexOf("\":");
		return json.substring(index+2, json.length()-1);
	}

}
