package com.zhe.grain.controller;

import com.zhe.grain.service.GrainBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@RestController
@RequestMapping("/grain/brain")
public class GrainBrandController {

    @Autowired
    private GrainBrandService grainBrandService;


}
