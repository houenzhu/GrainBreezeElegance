package com.zhe.grain.utils;

import com.zhe.grain.entity.AdminEntity;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 存放线程共享对象
 */

public class ThreadLocalUtil {

    private static final ThreadLocal<AdminEntity> THREAD_LOCAL = new ThreadLocal<>();

    public static void setThreadLocalVal(AdminEntity adminEntity) {
        THREAD_LOCAL.set(adminEntity);
    }

    public static AdminEntity getThreadLocalVal() {
        return THREAD_LOCAL.get();
    }

}
