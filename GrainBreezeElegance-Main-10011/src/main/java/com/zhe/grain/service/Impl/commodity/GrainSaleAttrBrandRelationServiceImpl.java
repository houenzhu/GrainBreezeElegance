package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.entity.GrainSaleAttrBrandRelation;
import com.zhe.grain.mapper.commodity.GrainBrandMapper;
import com.zhe.grain.mapper.commodity.GrainSaleAttrBrandRelationMapper;
import com.zhe.grain.service.commodity.GrainSaleAttrBrandRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 销售属性和品牌关联表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-12
 */
@Service
@Slf4j
public class GrainSaleAttrBrandRelationServiceImpl
        extends ServiceImpl<GrainSaleAttrBrandRelationMapper, GrainSaleAttrBrandRelation>
        implements GrainSaleAttrBrandRelationService {

    @Autowired
    private GrainBrandMapper grainBrandMapper;

    @Override
    public void saveSaleAttrBrandRelation(Long brandId, Long attrId) {
        GrainSaleAttrBrandRelation grainSaleAttrBrandRelation = new GrainSaleAttrBrandRelation();
        String brandName = grainBrandMapper.selectById(brandId).getName();
        grainSaleAttrBrandRelation.setSaleAttrId(attrId)
                .setBrandId(brandId)
                .setBrandName(brandName);
        this.baseMapper.insert(grainSaleAttrBrandRelation);
    }
}
