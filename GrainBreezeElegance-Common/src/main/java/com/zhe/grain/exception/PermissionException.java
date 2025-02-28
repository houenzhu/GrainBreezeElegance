package com.zhe.grain.exception;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class PermissionException extends RuntimeException {
    public PermissionException() {
    }

    public PermissionException(String message) {
        super(message);
    }
}
