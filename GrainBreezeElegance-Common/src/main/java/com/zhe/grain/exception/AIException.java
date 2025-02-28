package com.zhe.grain.exception;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 关于通义千问的异常
 */

public class AIException extends RuntimeException{
    public AIException() {
    }

    public AIException(String message) {
        super(message);
    }
}
