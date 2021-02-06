package com.vcmy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: IgnoreSecurity.java
 * @Description:  忽略验证
 * @version: 1.0.0
 * @author Rock
 * @date: 2018年5月8日 上午10:42:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSecurity {

}
