package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.entity.CommoditySkuImages;
import com.zhe.grain.mapper.commodity.CommoditySkuImagesMapper;
import com.zhe.grain.service.commodity.CommoditySkuImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * sku 图片 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
@Service
public class CommoditySkuImagesServiceImpl
        extends ServiceImpl<CommoditySkuImagesMapper, CommoditySkuImages>
        implements CommoditySkuImagesService {

    /**
     * 批量保存sku与image之间的关系
     * @param commoditySkuImages 实体类集合
     */
    @Override
    @Transactional
    public void saveBatchSkuImages(List<CommoditySkuImages> commoditySkuImages) {
        if (CollectionUtils.isEmpty(commoditySkuImages)) return;
        this.saveBatch(commoditySkuImages);
    }
}
