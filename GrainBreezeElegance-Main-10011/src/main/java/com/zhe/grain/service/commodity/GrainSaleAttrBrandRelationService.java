package com.zhe.grain.service.commodity;

import com.zhe.grain.entity.GrainSaleAttrBrandRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 销售属性和品牌关联表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-12
 */
public interface GrainSaleAttrBrandRelationService
        extends IService<GrainSaleAttrBrandRelation> {

    /**
     * 保存销售属性和品牌之间的关联关系
     */
    void saveSaleAttrBrandRelation(Long brandId, Long attrId);
}
