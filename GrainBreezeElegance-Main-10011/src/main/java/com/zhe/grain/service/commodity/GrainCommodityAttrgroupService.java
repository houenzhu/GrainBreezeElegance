package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttrgroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.domain.commodity.GrainCommodityAttrgroupCategoryRelation;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.commodity.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性分组 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
public interface GrainCommodityAttrgroupService extends IService<GrainCommodityAttrgroup> {
    Result<PageUtils> listByPage(Map<String, Object> params);
    Result<Object> batchDelete(Long[] attrGroupIds);

    /**
     * 根据分类id查询属性分组
     * @param categoryId
     * @return
     */
    List<GrainCommodityAttrgroupCategoryRelation> selectOne(Long categoryId);

    /**
     * 通过分类id获得对应拥有的属性分组及下面的基本属性
     * @param categoryId 分类id
     * @return AttrGroupWithAttrsVo集合
     */
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCategoryId(Long categoryId);
}
