package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.CommoditySpuInfoDesc;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品spu信息介绍 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
public interface CommoditySpuInfoDescService extends IService<CommoditySpuInfoDesc> {

    /**
     * 保存商品spu图片介绍
     * @param commoditySpuInfoDesc
     */
    void saveSpuDescInfo(CommoditySpuInfoDesc commoditySpuInfoDesc);
}
