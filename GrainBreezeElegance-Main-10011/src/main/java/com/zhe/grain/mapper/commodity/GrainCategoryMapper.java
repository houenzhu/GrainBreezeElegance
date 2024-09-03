package com.zhe.grain.mapper.commodity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhe.grain.domain.commodity.GrainCategoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface GrainCategoryMapper extends BaseMapper<GrainCategoryEntity> {
    List<GrainCategoryEntity> getNotSelectedCategory(@Param("brandId") Long brandId);
    List<GrainCategoryEntity> getNotSelectedCategoryByAttrGroupId(@Param("attrGroupId") Long attrGroupId);
    List<GrainCategoryEntity> getNotSelectedCategoryByAttrId(@Param("attrId") Long attrId);

}
