package com.zhe.grain;

import com.zhe.config.DefaultFeignConfig;
import com.zhe.config.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.zhe.grain.mapper")
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = "com.zhe.feign", defaultConfiguration = {FeignConfig.class, DefaultFeignConfig.class})
public class GrainBreezeEleganceMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrainBreezeEleganceMainApplication.class, args);
    }
}
