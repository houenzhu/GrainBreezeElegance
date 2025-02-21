package com.zhe.grain.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Configuration
public class DeepSeekConfig {


    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClient) {
        return chatClient.build();
    }
}
