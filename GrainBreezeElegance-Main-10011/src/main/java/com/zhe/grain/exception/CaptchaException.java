package com.zhe.grain.exception;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 验证码异常类
 */

public class CaptchaException extends RuntimeException{
    public CaptchaException() {
    }

    public CaptchaException(String message) {
        super(message);
    }
}
