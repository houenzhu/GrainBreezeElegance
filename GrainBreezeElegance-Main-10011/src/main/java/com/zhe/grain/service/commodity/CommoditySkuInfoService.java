package com.zhe.grain.service.commodity;

import com.zhe.grain.entity.CommoditySkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku 信息 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
public interface CommoditySkuInfoService extends IService<CommoditySkuInfo> {

    /**
     * 批量添加sku信息
     * @param commoditySkuInfos
     */
    void batchSaveSpuInfo(List<CommoditySkuInfo> commoditySkuInfos);
}
