package com.zhe.grain.constant;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 存放redis中的常量值
 */

public interface RedisConstant {
    String USER_TICKET_POSTFIX = "Bearer ";

    // 存放用户实体的key
    String USER_ENTITY_KEY = "admin:user:";

    // 验证码的key
    String CAPTCHA_KEY = "captcha:";

    // 通义千问聊天记录
    String TONG_YI_HISTORY = "chat:history:";

    // 管理员权限
    String ADMIN_PERMISSION = "admin:permission";

    // 商品分类属性分类
    String CATEGORY_LIST_TREE = "category:list:tree";
}
