package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.CommoditySkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.utils.PageUtils;

import java.util.List;
import java.util.Map;

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

    /**
     * sku信息列表
     */
    PageUtils skuInfoList(Map<String, Object> params);
}
