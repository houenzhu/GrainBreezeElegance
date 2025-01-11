package com.zhe.grain.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 自定义注解
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CheckArgs {
}
