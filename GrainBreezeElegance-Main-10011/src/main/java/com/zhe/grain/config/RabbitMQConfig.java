package com.zhe.grain.config;

import com.zhe.grain.constant.RabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Configuration
public class RabbitMQConfig {

    // 定义队列
    @Bean
    public Queue spuQueue() {
        return new Queue(RabbitMQConstant.SPU_QUEUE, true);
    }

    // 定义交换机
    @Bean
    public TopicExchange spuTopicExchange() {
        return new TopicExchange(RabbitMQConstant.SPU_EXCHANGE);
    }

    // 队列绑定交换机
    @Bean
    public Binding bindingSpuQueue() {
        return BindingBuilder.bind(spuQueue())
                .to(spuTopicExchange())
                .with(RabbitMQConstant.SPU_ROUTING_KEY); // 满足spu.#才能进入到spuQueue队列中
    }
}
