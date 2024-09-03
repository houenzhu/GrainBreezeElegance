package com.zhe.grain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.zhe.grain.mapper")
public class GrainBreezeEleganceMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrainBreezeEleganceMainApplication.class, args);
    }
}
