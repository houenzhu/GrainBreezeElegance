package com.zhe.grain.service.common;

import com.zhe.grain.utils.PageUtils;

import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 基础实现类
 */

public interface BaseService {

    /**
     * 分页
     * @param params
     * @return
     */
    PageUtils page(Map<String, Object> params);
}
