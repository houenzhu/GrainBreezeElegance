package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.entity.CommodityProductAttrValue;
import com.zhe.grain.mapper.commodity.CommodityProductAttrValueMapper;
import com.zhe.grain.service.commodity.CommodityProductAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * spu 基本属性值 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
@Service
public class CommodityProductAttrValueServiceImpl
        extends ServiceImpl<CommodityProductAttrValueMapper, CommodityProductAttrValue>
        implements CommodityProductAttrValueService {

    /**
     * 实现
     * @param productAttrValues
     */
    @Override
    @Transactional
    public void saveBatchProductAttrValue(List<CommodityProductAttrValue> productAttrValues) {
        this.saveBatch(productAttrValues);
    }
}
