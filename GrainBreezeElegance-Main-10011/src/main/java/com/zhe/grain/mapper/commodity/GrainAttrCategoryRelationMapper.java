package com.zhe.grain.mapper.commodity;

import com.zhe.grain.domain.commodity.GrainAttrCategoryRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品属性和商品分类的关联表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-19
 */
@Mapper
public interface GrainAttrCategoryRelationMapper
        extends BaseMapper<GrainAttrCategoryRelation> {
    void batchDelete(@Param("attrIds") List<Long> attrIds);
}
