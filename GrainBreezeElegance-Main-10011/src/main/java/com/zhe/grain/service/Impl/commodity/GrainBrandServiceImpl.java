package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.entity.GrainBrandEntity;
import com.zhe.grain.entity.GrainCategoryBrandRelation;
import com.zhe.grain.mapper.commodity.GrainBrandMapper;
import com.zhe.grain.mapper.commodity.GrainCategoryBrandRelationMapper;
import com.zhe.grain.service.commodity.GrainBrandService;
import com.zhe.grain.service.commodity.GrainCategoryBrainRelationService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
public class GrainBrandServiceImpl extends ServiceImpl<GrainBrandMapper, GrainBrandEntity>
        implements GrainBrandService {

    @Autowired
    private GrainBrandMapper grainBrandMapper;

    @Autowired
    private GrainCategoryBrandRelationMapper grainCategoryBrandRelationMapper;

    /**
     * 分页
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<GrainBrandEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(key)) {
            queryWrapper.eq("id", key).or().like("name", key);
        }
        IPage<GrainBrandEntity> page = this.page(
                new Query<GrainBrandEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    /**
     * 更新品牌数据
     * @param grainBrandEntity
     */
    @Override
    public void update(GrainBrandEntity grainBrandEntity) {
        grainBrandMapper.updateById(grainBrandEntity);
    }

    /**
     * 对接口进行实现
     * @param ids 品牌id
     */
    @Override
    @Transactional
    public void batchDeleteBrandIds(List<Long> ids) {
        // 批量删除品牌
        this.removeBatchByIds(ids);

        // 删除品牌与分类的关联关系
        grainCategoryBrandRelationMapper.delete(
                new LambdaQueryWrapper<GrainCategoryBrandRelation>()
                        .in(GrainCategoryBrandRelation::getBrandId, ids)
        );
    }
}
