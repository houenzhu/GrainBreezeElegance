package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.domain.commodity.CommoditySkuInfo;
import com.zhe.grain.mapper.commodity.CommoditySkuInfoMapper;
import com.zhe.grain.service.commodity.CommoditySkuInfoService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku 信息 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
@Service
@Slf4j
public class CommoditySkuInfoServiceImpl
        extends ServiceImpl<CommoditySkuInfoMapper, CommoditySkuInfo>
        implements CommoditySkuInfoService {

    @Override
    @Transactional
    public void batchSaveSpuInfo(List<CommoditySkuInfo> commoditySkuInfos) {
        this.saveBatch(commoditySkuInfos);
    }

    /**
     * SKU分页信息
     */
    @Override
    public PageUtils skuInfoList(Map<String, Object> params) {
        String key = (String) params.get("key");
        String brandId = (String) params.get("brandId");
        String catelogId = (String) params.get("catelogId");
        String min = (String) params.get("min");
        String max = (String) params.get("max");
        LambdaQueryWrapper<CommoditySkuInfo> lambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(key) && !"undefined".equals(key)) {
            lambdaQueryWrapper.eq(CommoditySkuInfo::getSkuId, key)
                    .or()
                    .like(CommoditySkuInfo::getSkuName, key);
        }
        if (StringUtils.isNotBlank(brandId) && !"undefined".equals(brandId) && !"0".equals(brandId)) {
            lambdaQueryWrapper.and(wrapper -> wrapper.eq(CommoditySkuInfo::getBrandId, brandId));
        }
        if (StringUtils.isNotBlank(catelogId) && !"undefined".equals(catelogId) && !"0".equals(catelogId)) {
            lambdaQueryWrapper.and(wrapper -> wrapper.eq(CommoditySkuInfo::getCatalogId, catelogId));
        }
        if (StringUtils.isNotBlank(min) && StringUtils.isNotBlank(max)) {
            if (!"0".equals(min) && !"0".equals(max)) {
                lambdaQueryWrapper.and(wrapper ->
                        wrapper.between(CommoditySkuInfo::getPrice, min, max));
            }
        }
        IPage<CommoditySkuInfo> page = this.page(
                new Query<CommoditySkuInfo>().getPage(params),
                lambdaQueryWrapper
        );
        return new PageUtils(page);
    }
}
