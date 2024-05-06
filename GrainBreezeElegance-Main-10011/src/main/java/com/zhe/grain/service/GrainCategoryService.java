package com.zhe.grain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.entity.GrainCategoryEntity;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface GrainCategoryService extends IService<GrainCategoryEntity> {

    /**
     * 获取树形分类列表
     * @return
     */
    List<GrainCategoryEntity> getCategoryList();

    /**
     * 添加结点
     * @param grainCategoryEntity
     */
    void addNode(GrainCategoryEntity grainCategoryEntity);

}
