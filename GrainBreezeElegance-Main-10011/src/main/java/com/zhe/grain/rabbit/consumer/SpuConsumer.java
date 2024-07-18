package com.zhe.grain.rabbit.consumer;

import cn.hutool.json.JSONUtil;
import com.zhe.grain.constant.RabbitMQConstant;
import com.zhe.grain.service.commodity.CommoditySpuInfoService;
import com.zhe.grain.vo.SpuSaveVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 商品发布消息消费者
 */

@Service
@Slf4j
public class SpuConsumer {

    @Autowired
    private CommoditySpuInfoService commoditySpuInfoService;

    /**
     * 消费商品发布消息
     * @param message
     */
    @RabbitListener(queues = RabbitMQConstant.SPU_QUEUE)
    public void receiveSpuMessage(String message) {
        log.info("接收到的信息 => {}", message);
        SpuSaveVO spuSaveVO = JSONUtil.toBean(message, SpuSaveVO.class);
        commoditySpuInfoService.saveSpuInfo0(spuSaveVO);
    }
}
