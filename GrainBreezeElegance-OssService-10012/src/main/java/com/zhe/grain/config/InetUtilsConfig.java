package com.zhe.grain.config;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * houen_zhu
 * 2024/09/21
 */
@Configuration
public class InetUtilsConfig {

    @Bean
    public InetUtilsProperties inetUtilsProperties() {
        return new InetUtilsProperties();
    }

    @Bean
    public InetUtils inetUtils(InetUtilsProperties properties) {
        return new InetUtils(properties);
    }
}
