package com.zhe.grain.service.commodity;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.domain.commodity.GrainCategoryBrandRelation;
import com.zhe.grain.domain.commodity.GrainCategoryEntity;
import com.zhe.grain.utils.Result;

import java.util.List;

/**
 * <p>
 * 品牌分类关联表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-11
 */
public interface GrainCategoryBrainRelationService extends IService<GrainCategoryBrandRelation> {
    // 添加关联
    Result<Object> addRelation(GrainCategoryBrandRelation grainCategoryBrandRelation);

    Result<List<GrainCategoryBrandRelation>> showRelation(Long brandId);

    /**
     * 返回未被选择的分类数据
     * @return
     */
    Result<List<GrainCategoryEntity>> getNotSelectedCategory(Long brandId);

    /**
     * 删除关联
     */
    Result<Object> removeRelation(Long id);

    /**
     * 根据分类id查询关联
     * @param categoryId
     * @return
     */
    List<GrainCategoryBrandRelation> getRelationByCategoryId(Long categoryId);

}
