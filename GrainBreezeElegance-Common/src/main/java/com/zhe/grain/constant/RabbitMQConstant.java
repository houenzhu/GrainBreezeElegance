package com.zhe.grain.constant;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface RabbitMQConstant {

    /**
     * 通义千问的队列1
     */
    String TONG_YI_QUEUE1 = "tongyi_queue1";

    /**
     * 同义的交换机
     */
    String TONG_YI_EXCHANGE = "Tongyi.TopExchange";

    String TONG_YI_ROUTING_KEY = "#.tongyi.#";
}
