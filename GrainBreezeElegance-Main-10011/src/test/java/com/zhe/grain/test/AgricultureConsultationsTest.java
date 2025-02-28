package com.zhe.grain.test;

import com.zhe.grain.controller.aichat.TongYiAiController;
import com.zhe.grain.mapper.commodity.AgricultureConsultationsMapper;
import com.zhe.grain.service.commodity.AgricultureConsultationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@SpringBootTest
@ActiveProfiles("dev")
public class AgricultureConsultationsTest {

    @Autowired
    private AgricultureConsultationsMapper agricultureConsultationsMapper;

    @Autowired
    private AgricultureConsultationsService agricultureConsultationsService;

    @Autowired
    private TongYiAiController tongYiAiController;

    @Test
    public void t1() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", "2");
        map.put("limit", "5");
        map.put("type", "");
        System.out.println(agricultureConsultationsService.page(map));
    }


}
