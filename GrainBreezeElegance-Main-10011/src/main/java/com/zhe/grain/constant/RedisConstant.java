package com.zhe.grain.constant;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 存放redis中的常量值
 */

public interface RedisConstant {
    String USER_TICKET_POSTFIX = "Bearer-";

    // 存放用户实体的key
    String USER_ENTITY_KEY = "admin:user:";

    // 验证码的key
    String CAPTCHA_KEY = "captcha:";
}
