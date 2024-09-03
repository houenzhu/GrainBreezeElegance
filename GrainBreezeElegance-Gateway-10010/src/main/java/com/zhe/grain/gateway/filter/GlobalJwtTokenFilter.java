package com.zhe.grain.gateway.filter;

import com.zhe.grain.gateway.utils.MonoResponseUtil;
import com.zhe.grain.gateway.utils.RedisCache;
import com.zhe.grain.utils.JwtUtil;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.ResultMsgEnum;
import com.zhe.grain.utils.design.iterator.AnyClassBox;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Component
@Slf4j
public class GlobalJwtTokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisCache redisCache;


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
        AnyClassBox<String> stringAnyClassBox = new AnyClassBox<>(5);
        stringAnyClassBox.appendAll(Arrays.asList(UN_CHECK_PATHS));
        Iterator<String> iterator = stringAnyClassBox.iterator();
        while (iterator.hasNext()) {
            if (path.equals(iterator.next())) {
                return chain.filter(exchange);
            }
        }
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            // 说明需要登录服务或者没有携带token就访问其他服务
            return MonoResponseUtil.monoResponse(exchange.getResponse(),
                    Result.error(ResultMsgEnum.UNAUTHORIZED, null));
        }
        token = token.split(" ")[1];
        if (!checkToken(token)) {
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
