package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttrAttrgroupRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性和商品属性组的关联表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-16
 */
public interface GrainCommodityAttrAttrgroupRelationService
        extends IService<GrainCommodityAttrAttrgroupRelation> {

    /**
     * 批量添加商品基本属性和属性组之间的关联
     * @param relations 需要添加的对应
     * @return 添加成功或者是失败
     */
    boolean batchSaveAttrAttrGroupRelation(List<GrainCommodityAttrAttrgroupRelation> relations);

    /**
     * 批量删除商品基本属性和属性组之间的关联
     * @param relations
     * @return 删除的行数
     */
    int removeRelation(List<GrainCommodityAttrAttrgroupRelation> relations);
}
