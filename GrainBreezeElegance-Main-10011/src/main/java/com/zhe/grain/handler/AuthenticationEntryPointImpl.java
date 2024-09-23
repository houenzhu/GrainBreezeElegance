package com.zhe.grain.handler;

import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.ResultMsgEnum;
import com.zhe.grain.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 认证失败处理
 */

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        WebUtil.render(response, Result.error(ResultMsgEnum.UNAUTHORIZED,
                "用户名或密码错误", null));
    }
}
