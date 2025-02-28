package com.zhe.grain.gateway.filter;

import com.zhe.grain.gateway.utils.MonoResponseUtil;
import com.zhe.grain.utils.JwtUtil;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.ResultMsgEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Component
@Slf4j
public class GlobalJwtTokenFilter implements GlobalFilter, Ordered {

    // 定义白名单路径
    private static final String[] UN_CHECK_PATHS = {
            "/api/grain/user/login",
            "/api/grain/user/captcha"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("请求: {}, 经过网关过滤器", path);
        // 使用iterator模式
        List<String> whitePath = Arrays.asList(UN_CHECK_PATHS);
        if (whitePath.contains(path)) {
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            // 说明需要登录服务或者没有携带token就访问其他服务
            return MonoResponseUtil.monoResponse(exchange.getResponse(),
                    Result.error(ResultMsgEnum.UNAUTHORIZED, null));
        }
        String tokenSuffix = token.split(" ")[1];
        if (!checkToken(tokenSuffix)) {
            return MonoResponseUtil.monoResponse(exchange.getResponse(),
                    Result.error(ResultMsgEnum.UNAUTHORIZED, "您的登录信息有误!请刷新页面", null));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 校验token的用户合法性
     * @param token
     * @return
     */
    private boolean checkToken(String token) {
        String userId;
        try {
            Claims jwt = JwtUtil.parseJWT(token);
            userId = jwt.getSubject();
            if (StringUtils.isBlank(userId)) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
