package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.entity.GrainCategoryEntity;
import com.zhe.grain.entity.GrainCommodityAttrgroup;
import com.zhe.grain.entity.GrainCommodityAttrgroupCategoryRelation;
import com.zhe.grain.mapper.commodity.GrainCategoryMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrgroupCategoryRelationMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrgroupMapper;
import com.zhe.grain.service.commodity.GrainCategoryService;
import com.zhe.grain.service.commodity.GrainCommodityAttrgroupCategoryRelationService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.AttrGroupRelationVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 属性分组与分类关联表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
@Service
public class GrainCommodityAttrgroupCategoryRelationServiceImpl
        extends ServiceImpl<GrainCommodityAttrgroupCategoryRelationMapper, GrainCommodityAttrgroupCategoryRelation>
        implements GrainCommodityAttrgroupCategoryRelationService {

    @Autowired
    private GrainCommodityAttrgroupCategoryRelationMapper grainCommodityAttrgroupCategoryRelationMapper;

    @Autowired
    private GrainCategoryMapper grainCategoryMapper;

    @Autowired
    private GrainCategoryService grainCategoryService;

    @Autowired
    private GrainCommodityAttrgroupMapper grainCommodityAttrgroupMapper;

    /**
     * 分页查询该属性分组
     *
     * @param params
     * @return
     */
    @Override
    public Result<PageUtils> queryByPage(Map<String, Object> params) {
        QueryWrapper<GrainCommodityAttrgroupCategoryRelation> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq("id", key).or().like("category_name", key);
        }
        IPage<GrainCommodityAttrgroupCategoryRelation> page = this.page(
                new Query<GrainCommodityAttrgroupCategoryRelation>().getPage(params),
                queryWrapper
        );
        return Result.success(new PageUtils(page));
    }

    /**
     * 查询某个属性分组关联数据
     *
     * @param attrGroupId
     * @return
     */
    @Deprecated
    @Override
    public Result<List<GrainCommodityAttrgroupCategoryRelation>> queryAllRelation(Long attrGroupId) {
        if (attrGroupId == null) {
            return Result.error();
        }
        QueryWrapper<GrainCommodityAttrgroupCategoryRelation> queryWrapper =
                new QueryWrapper<GrainCommodityAttrgroupCategoryRelation>()
                        .eq("attr_group_id", attrGroupId);
        return Result.success(grainCommodityAttrgroupCategoryRelationMapper.selectList(queryWrapper));
    }

    /**
     * 分页查询该属性分组关联数据
     *
     * @param params
     * @return
     */
    @Override
    public Result<PageUtils> queryAllRelationByPage(Map<String, Object> params) {
        String attrGroupIdString = (String) params.get("attrGroupId");
        long attrGroupId = Long.parseLong(attrGroupIdString);
        // 创建条件构造器
        QueryWrapper<GrainCommodityAttrgroupCategoryRelation> queryWrapper =
                new QueryWrapper<GrainCommodityAttrgroupCategoryRelation>().eq("attr_group_id", attrGroupId);
        // 分页
        IPage<GrainCommodityAttrgroupCategoryRelation> page = this.page(
                new Query<GrainCommodityAttrgroupCategoryRelation>().getPage(params),
                queryWrapper
        );
        return Result.success(new PageUtils(page));
    }

    @Override
    public Result<List<GrainCategoryEntity>> queryCategoryNotSelected(Long attrGroupId) {
        List<GrainCategoryEntity> allNodes =
                grainCategoryMapper.getNotSelectedCategoryByAttrGroupId(attrGroupId);
        List<GrainCategoryEntity> collect = allNodes.stream().filter(grainCategoryEntity -> grainCategoryEntity.getParentId().equals(0L))
                .map(grainCategoryEntity -> grainCategoryEntity.setChildren(grainCategoryService.getChildrenByParentId(allNodes, grainCategoryEntity)))
                // 升序排序
                .sorted((category1, category2) -> (category1.getSort() == null ? 0 : category1.getSort())
                        - (category2.getSort() == null ? 0 : category2.getSort())
                ).toList();

        return Result.success(collect);
    }

    /**
     * 添加关联
     *
     * @param attrGroupRelationVO
     */
    @Override
    public boolean addRelation(AttrGroupRelationVO attrGroupRelationVO) {
        Long categoryId = attrGroupRelationVO.getCategoryId();
        Long attrGroupId = attrGroupRelationVO.getAttrGroupId();
        // 这里需要多表查询，可以开个新的线程提高效率
        new Thread(() -> {
            GrainCategoryEntity categoryEntity = grainCategoryMapper.selectById(categoryId);
            GrainCommodityAttrgroup grainCommodityAttrgroup = grainCommodityAttrgroupMapper.selectById(attrGroupId);
            GrainCommodityAttrgroupCategoryRelation grainCommodityAttrgroupCategoryRelation =
                    new GrainCommodityAttrgroupCategoryRelation();
            grainCommodityAttrgroupCategoryRelation
                    .setCategoryName(categoryEntity.getName())
                    .setCategoryId(categoryId)
                    .setAttrName(grainCommodityAttrgroup.getName())
                    .setAttrGroupId(attrGroupId);
            grainCommodityAttrgroupCategoryRelationMapper.insert(grainCommodityAttrgroupCategoryRelation);
        }).start();
        return true;
    }


}
