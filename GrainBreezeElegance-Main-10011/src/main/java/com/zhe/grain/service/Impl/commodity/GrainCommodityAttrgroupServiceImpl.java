package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhe.grain.entity.GrainCommodityAttr;
import com.zhe.grain.entity.GrainCommodityAttrAttrgroupRelation;
import com.zhe.grain.entity.GrainCommodityAttrgroup;
import com.zhe.grain.entity.GrainCommodityAttrgroupCategoryRelation;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrAttrgroupRelationMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrgroupCategoryRelationMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrgroupMapper;
import com.zhe.grain.service.commodity.GrainCommodityAttrgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性分组 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
@Service
public class GrainCommodityAttrgroupServiceImpl
        extends ServiceImpl<GrainCommodityAttrgroupMapper, GrainCommodityAttrgroup>
        implements GrainCommodityAttrgroupService {

    @Autowired
    private GrainCommodityAttrgroupCategoryRelationMapper grainCommodityAttrgroupCategoryRelationMapper;

    @Autowired
    private GrainCommodityAttrAttrgroupRelationMapper attrAttrgroupRelationMapper;

    @Autowired
    private GrainCommodityAttrMapper grainCommodityAttrMapper;



    /**
     * 分页展示(可以根据关键字id|name搜索)
     * @param params
     * @return
     */
    @Override
    public Result<PageUtils> listByPage(Map<String, Object> params) {
        System.out.println("params = " + params);
        String key = (String) params.get("key");
        QueryWrapper<GrainCommodityAttrgroup> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq("id", key).or().like("name", key);
        }
        IPage<GrainCommodityAttrgroup> page = this.page(
                new Query<GrainCommodityAttrgroup>().getPage(params),
                queryWrapper
        );
        return Result.success(new PageUtils(page));
    }

    /**
     * 批量删除属性分组id
     * 进行两表删除
     * @param attrGroupIds
     * @return
     */
    @Override
    @Transactional
    public Result<Object> batchDelete(Long[] attrGroupIds) {
        if (attrGroupIds.length == 0) {
            return Result.error();
        }
        new Thread(() -> removeRelations(attrGroupIds)).start();
        return Result.success("删除成功!", null);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<GrainCommodityAttrgroupCategoryRelation> selectOne(Long categoryId) {
        if (null == categoryId) {
            return null;
        }
        return grainCommodityAttrgroupCategoryRelationMapper
                .selectAllByCategoryId(categoryId);
    }

    /**
     * 通过分类id获得对应拥有的属性分组及下面的基本属性
     * @param categoryId 分类id
     * @return AttrGroupWithAttrsVo集合
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCategoryId(Long categoryId) {
        List<GrainCommodityAttrgroupCategoryRelation> categoryRelations = grainCommodityAttrgroupCategoryRelationMapper.selectList(
                new LambdaQueryWrapper<GrainCommodityAttrgroupCategoryRelation>()
                        .eq(GrainCommodityAttrgroupCategoryRelation::getCategoryId, categoryId)
        );
        return categoryRelations.stream().map(item -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            attrGroupWithAttrsVo.setId(item.getId());
            attrGroupWithAttrsVo.setName(item.getAttrName());
            Long attrGroupId = item.getAttrGroupId();
            List<GrainCommodityAttrAttrgroupRelation> relations = attrAttrgroupRelationMapper.selectList(
                    new QueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                            .eq("attr_group_id", attrGroupId)
            );
            // 收集属性id
            List<Long> attrIds = relations.stream()
                    .map(GrainCommodityAttrAttrgroupRelation::getAttrId)
                    .toList();
            // 批量查询
            List<GrainCommodityAttr> grainCommodityAttrs
                    = grainCommodityAttrMapper.selectByAttrIds(attrIds);
            // 将集合放入VO的属性
            attrGroupWithAttrsVo.setGrainCommodityAttrs(grainCommodityAttrs);
            return attrGroupWithAttrsVo;
        }).toList();
    }

    @Transactional
    public void removeRelations(Long[] attrGroupIds) {
        removeBatchByIds(Arrays.asList(attrGroupIds));
        grainCommodityAttrgroupCategoryRelationMapper.batchDelete(attrGroupIds);
    }
}
