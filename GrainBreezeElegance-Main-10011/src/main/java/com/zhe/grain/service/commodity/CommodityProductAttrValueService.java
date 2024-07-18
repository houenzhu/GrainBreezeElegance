package com.zhe.grain.service.commodity;

import com.zhe.grain.entity.CommodityProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu 基本属性值 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
public interface CommodityProductAttrValueService
        extends IService<CommodityProductAttrValue> {

    /**
     * 批量添加商品基本属性值
     * @param productAttrValues
     */
    void saveBatchProductAttrValue(List<CommodityProductAttrValue> productAttrValues);
}
