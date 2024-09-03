package com.zhe.grain.utils;

import com.zhe.grain.domain.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class SecurityUtil {

    public static Object returnPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public static Long returnUserId() {
        LoginUser userDetail = (LoginUser) returnPrincipal();
        return userDetail.getSysUser().getId();
    }
}
