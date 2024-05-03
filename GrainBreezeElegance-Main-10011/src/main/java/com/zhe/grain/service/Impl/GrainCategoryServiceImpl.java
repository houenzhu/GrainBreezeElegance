package com.zhe.grain.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.entity.GrainCategoryEntity;
import com.zhe.grain.mapper.GrainCategoryMapper;
import com.zhe.grain.service.GrainCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@Slf4j
public class GrainCategoryServiceImpl extends ServiceImpl<GrainCategoryMapper, GrainCategoryEntity>
        implements GrainCategoryService {

    @Autowired
    private GrainCategoryMapper grainCategoryMapper;

    /**
     * 获取树形商品分类列表
     * @return
     */
    @Override
    public List<GrainCategoryEntity> getCategoryList() {
        List<GrainCategoryEntity> all = grainCategoryMapper.selectList(null);
        List<GrainCategoryEntity> listTree = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentId().equals(0L))
                .map(grainCategoryEntity -> grainCategoryEntity.setChildren(this.getChildrenByParentId(all, grainCategoryEntity)))
                // 升序排序
                .sorted((category1, category2) -> (category1.getSort() == null ? 0 : category1.getSort())
                        - (category2.getSort() == null ? 0 : category2.getSort())
                )
                .collect(Collectors.toList());
        return listTree;
    }

    @Override
    public void addTopNode(GrainCategoryEntity grainCategoryEntity) {
        if (grainCategoryEntity.getParentId() == null) {
            return;
        }
        save(grainCategoryEntity);
    }

    /**
     * 构造子节点
     * @param all 所有的数据
     * @param root 根节点
     */
    private List<GrainCategoryEntity> getChildrenByParentId(List<GrainCategoryEntity> all, GrainCategoryEntity root) {
        List<GrainCategoryEntity> childrenTree = all.stream()
                .filter(grainCategoryEntity -> grainCategoryEntity.getParentId().equals(root.getId()))
                .map(grainCategoryEntity -> {
                    // 给每个分类设置子分类, 每个子分类都会变为根节点递归查找子节点
                    return grainCategoryEntity
                            .setChildren(getChildrenByParentId(all, grainCategoryEntity));
                })
                .sorted((category1, category2) -> (category1.getSort() == null ? 0 : category1.getSort())
                        - (category2.getSort() == null ? 0 : category2.getSort())
                )
                .collect(Collectors.toList());
        return childrenTree;
    }
}
