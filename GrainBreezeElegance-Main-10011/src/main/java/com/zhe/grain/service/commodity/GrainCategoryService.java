package com.zhe.grain.service.commodity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.domain.commodity.GrainCategoryEntity;

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

    List<GrainCategoryEntity> getChildrenByParentId(List<GrainCategoryEntity> all, GrainCategoryEntity root);

    /**
     * 批量删除结点以及关联关系
     * @param ids 选中的结点id
     */
    void batchDeleteNodes(List<Long> ids);

}
