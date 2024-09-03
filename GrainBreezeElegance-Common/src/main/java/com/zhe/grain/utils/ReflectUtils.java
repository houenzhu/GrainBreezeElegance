package com.zhe.grain.utils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 反射创建对象
 */

public class ReflectUtils {

    @SuppressWarnings("all")
    public static Object reflectObject(Class clazz, Map<String, Object> values) throws Exception {
        Object object = clazz.getConstructor(null).newInstance();
        Class superclass = clazz.getSuperclass();
        Field[] parentFields = superclass.getDeclaredFields();
        for (Field field : parentFields) {
            // 反射暴破
            field.setAccessible(true);
            values.entrySet().forEach(item -> {
                if (item.getKey().equals(field.getName())) {
                    Object value = item.getValue();
                    try {
                        field.set(object, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        Field[] childField = clazz.getDeclaredFields();
        for (Field field : childField) {
            field.setAccessible(true);
            values.entrySet().forEach(item -> {
                if (item.getKey().equals(field.getName())) {
                    Object value = item.getValue();
                    try {
                        field.set(object, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        return object;
    }
}
