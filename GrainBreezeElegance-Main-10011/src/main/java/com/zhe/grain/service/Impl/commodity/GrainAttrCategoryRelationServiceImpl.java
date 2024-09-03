package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.domain.commodity.GrainAttrCategoryRelation;
import com.zhe.grain.domain.commodity.GrainCategoryEntity;
import com.zhe.grain.mapper.commodity.GrainAttrCategoryRelationMapper;
import com.zhe.grain.mapper.commodity.GrainCategoryMapper;
import com.zhe.grain.service.commodity.GrainAttrCategoryRelationService;
import com.zhe.grain.service.commodity.GrainCategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 商品属性和商品分类的关联表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-19
 */
@Service
public class GrainAttrCategoryRelationServiceImpl
        extends ServiceImpl<GrainAttrCategoryRelationMapper, GrainAttrCategoryRelation>
        implements GrainAttrCategoryRelationService {

    @Autowired
    private GrainCategoryMapper grainCategoryMapper;

    @Autowired
    private GrainCategoryService grainCategoryService;


    /**
     * 通过传来的分类名删除商品属性和分类之间的关联
     *
     * @param params
     */
    @Override
    public boolean removeRelation(Map<String, Object> params) {
        // 设置标志位
        boolean flag = true;
        // 如何参数为空直接返回
        if (params.isEmpty()) {
            flag = false;
            return flag;
        }
        String attrStringId = (String) params.get("attrId");
        long attrId = Long.parseLong(attrStringId);
        String attrName = (String) params.get("categoryName");
        if (attrId == 0 && StringUtils.isBlank(attrName)) {
            flag = false;
            return flag;
        }
        // 通过名字索引找到对应分类的id, 前提是名字不能重复
        Long id = grainCategoryMapper.selectOne(
                new QueryWrapper<GrainCategoryEntity>().eq("name", attrName)
        ).getId();
        if (id == null) {
            flag = false;
            return flag;
        }
        // 删除操作
        int deleteRow = this.baseMapper.delete(
                new QueryWrapper<GrainAttrCategoryRelation>().eq("category_id", id)
                        .and(wrapper -> wrapper.eq("attr_id", attrId))
        );
        if (deleteRow < 1) { // 如果删除的行数少于1行, 则代表没有删除成功
            flag = false;
            return flag;
        }
        return flag;
    }

    /**
     * 获取没有被选中的分类
     * @param attrId 商品属性id
     * @return
     */
    @Override
    public List<GrainCategoryEntity> getNotSelectedCategory(Long attrId) {
        List<GrainCategoryEntity> all =
                grainCategoryMapper.getNotSelectedCategoryByAttrId(attrId);
        List<GrainCategoryEntity> children = all.stream()
                .filter(category -> category.getParentId().equals(0L))
                .map(category -> category.setChildren(grainCategoryService
                        .getChildrenByParentId(all, category)))
                .sorted((category1, category2) -> (category1.getSort() == null ? 0 : category1.getSort())
                        - (category2.getSort() == null ? 0 : category2.getSort()))
                .toList();
        return children;
    }

    /**
     * 通过传来的分类id和商品属性id添加关联
     * @return
     */
    @Override
    public boolean addRelation(GrainAttrCategoryRelation grainAttrCategoryRelation) {
        if (Objects.isNull(grainAttrCategoryRelation)) {
            return false;
        }
        Long categoryId = grainAttrCategoryRelation.getCategoryId();
        Long attrId = grainAttrCategoryRelation.getAttrId();
        if (null == categoryId || null == attrId) {
            return false;
        }
        int insert = this.baseMapper.insert(grainAttrCategoryRelation);
        return insert > 0;
    }
}
