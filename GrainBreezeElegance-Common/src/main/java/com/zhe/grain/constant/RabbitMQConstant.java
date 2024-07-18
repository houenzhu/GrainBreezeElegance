package com.zhe.grain.constant;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface RabbitMQConstant {

    /**
     * 商品队列及交换机
     */
    String SPU_QUEUE = "spu_queue";
    String SPU_EXCHANGE = "spu_exchange";
    String SPU_ROUTING_KEY = "spu.#";
}
