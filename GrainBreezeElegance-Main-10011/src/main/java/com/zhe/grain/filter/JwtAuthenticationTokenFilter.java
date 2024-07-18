package com.zhe.grain.filter;

import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.entity.LoginUser;
import com.zhe.grain.utils.JwtUtil;
import com.zhe.grain.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 责任链
 * 进行jwt验证
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // 因为网关的过滤器已经判断了token是否合法，
        // 所以我们可以直接存入安全上下文
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) { // 可能需要登录，或者没有登录就想访问其他接口
            filterChain.doFilter(request, response);
            return;
        }
        token = token.split(" ")[1];
        LoginUser loginUser = parseToken(token);
        if (Objects.isNull(loginUser)) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,
                        null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 当有操作的时候进行时间重置
        redisCache.setCacheObject(RedisConstant.USER_ENTITY_KEY + loginUser.getSysUser().getId(), loginUser,
                60 * 60 * 1000, TimeUnit.MILLISECONDS);
        filterChain.doFilter(request, response);
    }

    private LoginUser parseToken(String token) {
        String userId;
        LoginUser loginUser;
        try {
            Claims jwt = JwtUtil.parseJWT(token);
            userId = jwt.getSubject();
            loginUser = redisCache.getCacheObject(RedisConstant.USER_ENTITY_KEY + userId);
            if (Objects.isNull(loginUser)) {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return loginUser;
    }
}
