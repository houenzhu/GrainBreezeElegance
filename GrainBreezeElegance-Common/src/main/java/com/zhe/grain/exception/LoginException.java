package com.zhe.grain.exception;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class LoginException extends RuntimeException{
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
