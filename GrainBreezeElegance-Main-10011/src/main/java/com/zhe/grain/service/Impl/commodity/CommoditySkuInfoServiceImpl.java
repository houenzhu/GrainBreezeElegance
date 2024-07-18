package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.entity.CommoditySkuInfo;
import com.zhe.grain.mapper.commodity.CommoditySkuInfoMapper;
import com.zhe.grain.service.commodity.CommoditySkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * sku 信息 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
@Service
public class CommoditySkuInfoServiceImpl
        extends ServiceImpl<CommoditySkuInfoMapper, CommoditySkuInfo>
        implements CommoditySkuInfoService {

    @Override
    @Transactional
    public void batchSaveSpuInfo(List<CommoditySkuInfo> commoditySkuInfos) {
        this.saveBatch(commoditySkuInfos);
    }
}
