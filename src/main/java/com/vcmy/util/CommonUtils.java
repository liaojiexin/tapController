package com.vcmy.util;

import com.vcmy.common.Constant;
import org.apache.commons.lang3.StringUtils;

import java.util.StringTokenizer;

public class CommonUtils {

	private CommonUtils(){
			
		}

	public static String parseUri(String text, String... args) {
		return parse(text, Constant.PLACEHOLDER, args);
	}

	/**
	 * @Title: parse
	 * @Description: 将url中占位符替换成真实参数
	 * @param text
	 *            待替换的文本
	 * @param placeholder
	 *            占位符
	 * @param args
	 *            替换参数
	 * @return String 返回类型
	 */
	public static String parse(String text, String placeholder, String... args) {
		if (args == null || args.length == 0) {
			return text;
		}
		if (StringUtils.isEmpty(text)) {
			return StringUtils.EMPTY;
		}
		if (text.indexOf(placeholder) == -1) {
			return text;
		}
		int argsIndex = 0;
		StringBuilder sb = new StringBuilder();
		StringTokenizer src = new StringTokenizer(text, placeholder);
		while (src.hasMoreTokens()) {
			String value = (argsIndex <= args.length - 1) ? args[argsIndex] : placeholder;
			if (src.countTokens() == 1 && !text.endsWith(placeholder)) {
				sb.append(src.nextToken());
			} else {
				sb.append(src.nextToken()).append(value);
			}
			argsIndex++;
		}
		return sb.toString();
	}
}
