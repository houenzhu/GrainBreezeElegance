package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhe.grain.entity.GrainBrandEntity;
import com.zhe.grain.entity.GrainCategoryBrandRelation;
import com.zhe.grain.entity.GrainCategoryEntity;
import com.zhe.grain.mapper.commodity.GrainBrandMapper;
import com.zhe.grain.mapper.commodity.GrainCategoryBrandRelationMapper;
import com.zhe.grain.mapper.commodity.GrainCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.service.commodity.GrainCategoryBrainRelationService;
import com.zhe.grain.service.commodity.GrainCategoryService;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品牌分类关联表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-11
 */
@Service
public class GrainCategoryBrandRelationServiceImpl
        extends ServiceImpl<GrainCategoryBrandRelationMapper, GrainCategoryBrandRelation>
        implements GrainCategoryBrainRelationService {

    @Autowired
    private GrainCategoryBrandRelationMapper grainCategoryBrandRelationMapper;

    @Autowired
    private GrainBrandMapper grainBrandMapper;

    @Autowired
    private GrainCategoryMapper grainCategoryMapper;
    @Autowired
    private GrainCategoryService grainCategoryService;

    /**
     * 添加关联
     *
     * @param grainCategoryBrandRelation
     * @return
     */
    @Override
    public Result<Object> addRelation(GrainCategoryBrandRelation grainCategoryBrandRelation) {
        // 查出两个id对应的分类名称和品牌名称
        Long categoryId = grainCategoryBrandRelation.getCategoryId();
        Long brandId = grainCategoryBrandRelation.getBrandId();
        GrainCategoryEntity categoryEntity = grainCategoryMapper.selectOne(
                new QueryWrapper<GrainCategoryEntity>().eq("id", categoryId)
        );
        GrainBrandEntity brandEntity = grainBrandMapper.selectOne(
                new QueryWrapper<GrainBrandEntity>().eq("id", brandId)
        );
        new Thread(() -> {
            GrainCategoryBrandRelation categoryBrainRelation =
                    new GrainCategoryBrandRelation(null, brandId, categoryId,
                            brandEntity.getName(), categoryEntity.getName());
            grainCategoryBrandRelationMapper.insert(categoryBrainRelation);
        }).start();
        return Result.success("添加成功", null);
    }

    /**
     * 通过该品牌id查看已经关联了所以的分类
     *
     * @param brandId
     * @return
     */
    @Override
    public Result<List<GrainCategoryBrandRelation>> showRelation(Long brandId) {
        List<GrainCategoryBrandRelation> grainCategoryBrandRelations =
                grainCategoryBrandRelationMapper.selectList(
                        new QueryWrapper<GrainCategoryBrandRelation>()
                                .eq("brand_id", brandId)
                );
        return Result.success(grainCategoryBrandRelations);
    }

    /**
     * 获取未被关联的分类
     *
     * @return
     */
    @Override
    public Result<List<GrainCategoryEntity>> getNotSelectedCategory(Long brandId) {
        List<GrainCategoryEntity> all =
                grainCategoryMapper.getNotSelectedCategory(brandId);
        List<GrainCategoryEntity> children = all.stream()
                .filter(category -> category.getParentId().equals(0L))
                .map(category -> category.setChildren(grainCategoryService
                        .getChildrenByParentId(all, category)))
                .sorted((category1, category2) -> (category1.getSort() == null ? 0 : category1.getSort())
                        - (category2.getSort() == null ? 0 : category2.getSort()))
                .toList();
        return Result.success(children);
    }

    /**
     * 删除关联
     * @param id
     * @param id
     * @return
     */
    @Override
    public Result<Object> removeRelation(Long id) {
        int delete = grainCategoryBrandRelationMapper.delete(
                new QueryWrapper<GrainCategoryBrandRelation>()
                        .eq("id", id)
        );
        return delete > 0 ? Result.success("删除成功!", null)
                : Result.error("删除失败!", null);
    }

    /**
     * 通过分类id获取关联
     * @param categoryId
     * @return
     */
    @Override
    public List<GrainCategoryBrandRelation> getRelationByCategoryId(Long categoryId) {
        if (Objects.isNull(categoryId) || categoryId == 0) {
            return null;
        }
        // 查询到关联, 一对多的关系
        List<GrainCategoryBrandRelation> brandRelations = this.baseMapper.selectList(
                new LambdaQueryWrapper<GrainCategoryBrandRelation>()
                        .eq(GrainCategoryBrandRelation::getCategoryId, categoryId)
        );
        if (CollectionUtils.isEmpty(brandRelations)) {
            return null;
        }
        return brandRelations;
    }
}
