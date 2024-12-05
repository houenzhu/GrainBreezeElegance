package com.zhe.grain.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 反射创建对象
 */

public class ReflectUtils {

    @SuppressWarnings("all")
    public static <T> T reflectObject(Class clazz, Map<String, Object> values) throws Exception {
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
        return (T) object;
    }

    public static Map<String, Object> constructMapParams(Object object) throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), field.get(object));
        }
        return map;
    }
}
