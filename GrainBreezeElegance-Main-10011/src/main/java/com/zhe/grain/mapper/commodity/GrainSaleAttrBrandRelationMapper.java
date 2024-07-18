package com.zhe.grain.mapper.commodity;

import com.zhe.grain.entity.GrainSaleAttrBrandRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 销售属性和品牌关联表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-12
 */
@Mapper
public interface GrainSaleAttrBrandRelationMapper
        extends BaseMapper<GrainSaleAttrBrandRelation> {

    /**
     * 批量删除销售属性和品牌关联
     * @param attrIds
     */
    void batchDeleteSaleAttrBrandRelation(@Param("attrIds") List<Long> attrIds);
}
