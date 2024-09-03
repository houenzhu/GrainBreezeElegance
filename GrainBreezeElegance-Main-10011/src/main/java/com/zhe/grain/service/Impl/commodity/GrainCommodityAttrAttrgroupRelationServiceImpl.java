package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttrAttrgroupRelation;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrAttrgroupRelationMapper;
import com.zhe.grain.service.commodity.GrainCommodityAttrAttrgroupRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 商品属性和商品属性组的关联表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-16
 */
@Service
public class GrainCommodityAttrAttrgroupRelationServiceImpl
        extends ServiceImpl<GrainCommodityAttrAttrgroupRelationMapper, GrainCommodityAttrAttrgroupRelation>
        implements GrainCommodityAttrAttrgroupRelationService {

    /**
     * 批量添加属性和属性分组的关联
     * @param relations 需要添加的对应
     * @return
     */
    @Override
    @Transactional
    public boolean batchSaveAttrAttrGroupRelation(List<GrainCommodityAttrAttrgroupRelation> relations) {
        if (CollectionUtils.isEmpty(relations)) {
            return false;
        }
        return saveBatch(relations);
    }

    /**
     * 批量删除属性和属性分组的关联
     * @param relations
     * @return
     */
    @Override
    public int removeRelation(List<GrainCommodityAttrAttrgroupRelation> relations) {
        int row = 0;
        if (CollectionUtils.isEmpty(relations)) {
            return row;
        }
        row = this.baseMapper.batchDeleteRelation(relations);
        return row;
    }
}
