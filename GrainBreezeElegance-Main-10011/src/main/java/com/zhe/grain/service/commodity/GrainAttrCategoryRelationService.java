package com.zhe.grain.service.commodity;

import com.zhe.grain.entity.GrainAttrCategoryRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.entity.GrainCategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性和商品分类的关联表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-19
 */
public interface GrainAttrCategoryRelationService
        extends IService<GrainAttrCategoryRelation> {

    /**
     * 通过传来的分类名删除商品属性和分类之间的关联
     * @param params
     */
    boolean removeRelation(Map<String, Object> params);
    List<GrainCategoryEntity> getNotSelectedCategory(Long attrId);

    /**
     * 通过传来的分类id和商品属性id添加关联
     * @return
     */
    boolean addRelation(GrainAttrCategoryRelation grainAttrCategoryRelation);


}
