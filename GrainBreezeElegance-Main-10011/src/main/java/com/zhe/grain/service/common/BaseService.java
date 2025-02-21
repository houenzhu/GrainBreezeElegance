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

    /**
     * 自定义获取总数
     * @return
     */
    default Integer getTotalCount(Map<String, Object> params) {
        return 0;
    }

    /**
     * 自定义获取每页数量
     * @return
     */
    default Integer getSize(Map<String, Object> params) {
        return Integer.parseInt((String) params.get("limit"));
    }

    /**
     * 自定义获取当前页
     * @return
     */
    default Integer getCurrentPage(Map<String, Object> params) {
        return Integer.parseInt((String) params.get("page"));
    }
}
