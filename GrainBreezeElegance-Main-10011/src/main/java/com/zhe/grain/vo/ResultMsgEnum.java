package com.zhe.grain.vo;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 状态码和状态消息枚举类
 */

@Getter
public enum ResultMsgEnum {
    // 通用枚举
    SUCCESS(HttpStatus.OK.value(), "OK"),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "哎呀~服务端出现异常了，请通知管理员~"),


    // 登录部分的枚举
    LOGIN_SUCCESS(HttpStatus.OK.value(), "登录成功"),
    LOGIN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "登陆失败, 请检查用户名或密码!"),
    PASSWORD_ERROR(HttpStatus.FORBIDDEN.value(), "密码错误!"),
    INVALID_COOKIE(HttpStatus.FORBIDDEN.value(), "未登录, 请重新登录"),

    // 注册枚举
    REGISTER_SUCCESS(HttpStatus.OK.value(), "注册成功!"),
    REGISTER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "注册失败, 请重新注册");

    private final Integer code;
    private final String msg;

    ResultMsgEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}