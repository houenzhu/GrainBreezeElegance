package com.zhe.grain.rabbit.provider;

import com.zhe.grain.constant.RabbitMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 商品发布消息生产者
 */

@Service
@Slf4j
public class SpuProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSaveSpuMessage(String message) {
        log.info("发送的消息是 => {}", message);
        rabbitTemplate.convertAndSend(RabbitMQConstant.SPU_EXCHANGE,
                "spu.save.message", message);
    }
}
