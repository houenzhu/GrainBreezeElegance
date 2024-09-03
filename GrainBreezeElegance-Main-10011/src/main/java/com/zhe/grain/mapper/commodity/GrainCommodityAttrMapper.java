package com.zhe.grain.mapper.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品属性表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-08
 */
@Mapper
public interface GrainCommodityAttrMapper extends BaseMapper<GrainCommodityAttr> {

    /**
     * 通过所属基本属性id查询属性
     * @param ids
     * @return
     */
    List<GrainCommodityAttr> selectByAttrIds(@Param("ids") List<Long> ids);

    /**
     * 查看还没有被关联的商品基本属性
     * @param ids
     * @return
     */
    List<GrainCommodityAttr> selectNotInAttrIds(@Param("ids") List<Long> ids);
}
