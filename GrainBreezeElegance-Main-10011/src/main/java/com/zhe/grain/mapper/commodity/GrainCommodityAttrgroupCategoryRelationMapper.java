package com.zhe.grain.mapper.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttrgroupCategoryRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 属性分组与分类关联表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
public interface GrainCommodityAttrgroupCategoryRelationMapper
        extends BaseMapper<GrainCommodityAttrgroupCategoryRelation> {
    boolean batchDelete(@Param("attrGroupIds") Long[] attrGroupIds);
    List<GrainCommodityAttrgroupCategoryRelation> selectAllByCategoryId(@Param("categoryId") Long categoryId);
}
