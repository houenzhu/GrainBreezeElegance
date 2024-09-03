package com.zhe.grain.mapper.commodity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhe.grain.domain.commodity.GrainCategoryBrandRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 品牌分类关联表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-11
 */
@Mapper
public interface GrainCategoryBrandRelationMapper
        extends BaseMapper<GrainCategoryBrandRelation> {

}
