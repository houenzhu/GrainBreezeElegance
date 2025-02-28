package com.zhe.grain.expression;

import com.zhe.grain.domain.user.LoginUser;
import com.zhe.grain.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 自定义表达式
 */

@Component("zhe")
@Slf4j
public class SelfExpressionRoot {
    public boolean hasAuthority(String role) {
        LoginUser loginUser = (LoginUser) SecurityUtil.returnPrincipal();
        List<String> permissions = loginUser.getPermissions();
        log.info("注解上的权限: {}", role);
        // 如果没有通配符, 则需要在容器中寻找有无具体的权限
        for (String permission : permissions) {
            String rex = "^" + permission.replace("*", ".*") + "$";
            Pattern pattern = Pattern.compile(rex);
            Matcher matcher = pattern.matcher(role);
            if (matcher.find()) {
                return true;
            }
        }
        return permissions.contains(role);
    }

    public boolean hasAnyAuthority(String... roles) {
        LoginUser loginUser = (LoginUser) SecurityUtil.returnPrincipal();
        List<String> permissions = loginUser.getPermissions();
        for (String permission : permissions) {
            String rex = "^" + permission.replace("*", ".*") + "$";
            Pattern pattern = Pattern.compile(rex);
            for (String role : roles) {
                Matcher matcher = pattern.matcher(role);
                if (matcher.find()) {
                    return true;
                }
                if (permissions.contains(role)) {
                    return true;
                }
            }
        }
        return false;
    }
}
