package com.zhe.grain.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @version 1.0
 * @Author 朱厚恩
 * MD5工具类: 根据密码设计方案提供相应的加密方案
 */

public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    // 准备salt [前端使用的salt]
//    public static final String SALT = "Z79FLD!U";
    public static final String SALT = "4tIY5VcX";

    // 一次加密加盐方法 --> 中间salt
    public static String inputPassToMidPass(String inputPass) {
        String str = SALT.charAt(0) + inputPass + SALT.charAt(6);
        return md5(str);
    }

    // 第二次加密加盐, 把中间密码转成存放到数据库的密码
    // 类似MD5(MD5(password + salt) + salt2)
    public static String midPassToDBPass(String midPass, String salt) {
        String str = salt.charAt(1) + midPass + salt.charAt(5);
        return md5(str);
    }

    // 将明文直接转成DB中的md5密码
    public static String inputToDBPass(String inputPass, String salt) {
        return midPassToDBPass(inputPassToMidPass(inputPass), salt);
    }

    public static void main(String[] args) {
        System.out.println(inputToDBPass("admin", "FONf71Bc"));
    }

}
