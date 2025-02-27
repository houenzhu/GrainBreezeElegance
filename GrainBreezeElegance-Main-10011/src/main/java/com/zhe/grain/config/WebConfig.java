package com.zhe.grain.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 自定义拦截器和解析器
 */

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AdminArgumentResolver adminArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminArgumentResolver);
    }
}
