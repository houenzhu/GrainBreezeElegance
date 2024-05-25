package com.zhe.grain.config;

import com.zhe.grain.interceptor.AdminAccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 自定义拦截器和解析器
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminArgumentResolver adminArgumentResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminArgumentResolver);
    }
}
