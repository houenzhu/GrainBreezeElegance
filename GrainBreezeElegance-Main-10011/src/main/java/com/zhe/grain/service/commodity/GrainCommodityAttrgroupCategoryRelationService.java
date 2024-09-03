package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.GrainCategoryEntity;
import com.zhe.grain.domain.commodity.GrainCommodityAttrgroupCategoryRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.commodity.AttrGroupRelationVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 属性分组与分类关联表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
public interface GrainCommodityAttrgroupCategoryRelationService
        extends IService<GrainCommodityAttrgroupCategoryRelation> {

    /**
     * 分页查询属性分组关联数据
     * @param params
     * @return
     */
    Result<PageUtils> queryByPage(Map<String, Object> params);

    /**
     * 查询某个属性分组关联数据
     * @return
     */
    @Deprecated // 该方法已被启用，使用queryAllRelationByPage分页
    Result<List<GrainCommodityAttrgroupCategoryRelation>> queryAllRelation(Long attrGroupId);

    /**
     * 分页查询属性分组关联数据
     * @param params
     * @return
     */
    Result<PageUtils> queryAllRelationByPage(Map<String, Object> params);

    /**
     * 查询没有被选择的所有分类，按照选项结构分类
     * @param attrGroupId
     * @return
     */
    Result<List<GrainCategoryEntity>> queryCategoryNotSelected(Long attrGroupId);

    /**
     * 添加属性分组与分类关联数据
     */
    boolean addRelation(AttrGroupRelationVO attrGroupRelationVO);

}
