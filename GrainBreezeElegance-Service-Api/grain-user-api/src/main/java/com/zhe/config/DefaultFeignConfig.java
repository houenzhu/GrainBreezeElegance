package com.zhe.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Configuration
public class DefaultFeignConfig {

    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
