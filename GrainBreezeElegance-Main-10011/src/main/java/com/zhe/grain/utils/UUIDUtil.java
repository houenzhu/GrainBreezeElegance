package com.zhe.grain.utils;

import java.util.UUID;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 生成uuid的工具类
 */

public class UUIDUtil {

    /**
     * uuid + 时间戳
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
    }

}
