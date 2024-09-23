package com.zhe.grain.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Configuration
public class OssConfig {

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.secret-key}")
    private String accessSecretKey;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessId, accessSecretKey);
    }
}
