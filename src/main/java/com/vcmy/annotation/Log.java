package com.vcmy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Log.java
 * @Description:  自定义操作日志记录注解
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午10:52:10
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	/** 模块 */
	String module() default "";

	/** 动作 */
	String action() default "";
}
