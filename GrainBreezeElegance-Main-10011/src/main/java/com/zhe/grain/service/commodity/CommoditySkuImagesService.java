package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.CommoditySkuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku 图片 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
public interface CommoditySkuImagesService extends IService<CommoditySkuImages> {

    /**
     * 批量保存sku中的图片信息
     * @param commoditySkuImages 实体类集合
     */
    void saveBatchSkuImages(List<CommoditySkuImages> commoditySkuImages);
}
