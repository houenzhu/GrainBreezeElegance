package com.zhe.grain.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class PasswordEncodeUtil {

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String passwordEncode(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(password);
        System.out.println("encode = " + encodePassword);
        return encodePassword;
    }
}
