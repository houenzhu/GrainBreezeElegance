package com.zhe.grain.mapper.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttrAttrgroupRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品属性和商品属性组的关联表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-16
 */
@Mapper
public interface GrainCommodityAttrAttrgroupRelationMapper
        extends BaseMapper<GrainCommodityAttrAttrgroupRelation> {
    void batchDeleteAttrId(@Param("attrIds") List<Long> attrIds);

    /**
     * 批量删除
     * @param relations
     * @return
     */
    int batchDeleteRelation(@Param("relations") List<GrainCommodityAttrAttrgroupRelation> relations);
}
