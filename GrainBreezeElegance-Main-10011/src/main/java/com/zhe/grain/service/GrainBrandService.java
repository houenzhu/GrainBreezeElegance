package com.zhe.grain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.entity.GrainBrandEntity;
import com.zhe.grain.utils.PageUtils;

import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */


public interface GrainBrandService extends IService<GrainBrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
