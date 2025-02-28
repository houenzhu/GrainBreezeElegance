package com.zhe.grain.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 加上此注解才能访问AdminController的接口
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminLoginAnnotation {

}
