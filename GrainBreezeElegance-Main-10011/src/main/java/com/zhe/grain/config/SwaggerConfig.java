package com.zhe.grain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 2024/09/03
 * Swagger2配置
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {
        Profiles profiles = Profiles.of("dev", "prod");
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("主测试环境")
                .enable(flag)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhe.grain.controller"))
                .build();
    }

    // 配置Swagger信息
    private ApiInfo apiInfo() {
        return new ApiInfo("仙涌村智慧农业管理平台接口文档", "我亦无他, 唯手熟尔", "1.0", "urn:tos",
                new Contact("houen_zhu", "http://www.baidu.com", "759959801@qq.com"),
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());

    }
}
