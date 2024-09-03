package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.domain.commodity.CommoditySpuInfoDesc;
import com.zhe.grain.mapper.commodity.CommoditySpuInfoDescMapper;
import com.zhe.grain.service.commodity.CommoditySpuInfoDescService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品spu信息介绍 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
@Service
public class CommoditySpuInfoDescServiceImpl
        extends ServiceImpl<CommoditySpuInfoDescMapper, CommoditySpuInfoDesc>
        implements CommoditySpuInfoDescService {

    @Override
    public void saveSpuDescInfo(CommoditySpuInfoDesc commoditySpuInfoDesc) {
        this.baseMapper.insert(commoditySpuInfoDesc);
    }
}
