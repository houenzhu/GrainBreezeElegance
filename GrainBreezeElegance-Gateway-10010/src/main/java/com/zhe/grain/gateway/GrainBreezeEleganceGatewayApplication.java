package com.zhe.grain.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@SpringBootApplication
@EnableDiscoveryClient
public class GrainBreezeEleganceGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrainBreezeEleganceGatewayApplication.class, args);
    }
}
