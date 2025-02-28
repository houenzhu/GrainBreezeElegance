package com.zhe.grain.service.Impl.commodity;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.domain.commodity.*;
import com.zhe.grain.mapper.commodity.CommoditySpuInfoMapper;
import com.zhe.grain.mapper.commodity.GrainBrandMapper;
import com.zhe.grain.mapper.commodity.GrainCategoryMapper;
import com.zhe.grain.mapper.commodity.GrainCommodityAttrMapper;
import com.zhe.grain.rabbit.provider.SpuProvider;
import com.zhe.grain.service.commodity.*;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.utils.WebUtil;
import com.zhe.grain.vo.commodity.BaseAttrs;
import com.zhe.grain.vo.commodity.Images;
import com.zhe.grain.vo.commodity.Skus;
import com.zhe.grain.vo.commodity.SpuSaveVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 商品spu信息 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
@Service
@Slf4j
@AllArgsConstructor
public class CommoditySpuInfoServiceImpl
        extends ServiceImpl<CommoditySpuInfoMapper, CommoditySpuInfo>
        implements CommoditySpuInfoService {

    private final SpuProvider spuProvider;

    private final CommoditySpuInfoDescService commoditySpuInfoDescService;

    private final CommoditySpuImagesService commoditySpuImagesService;

    private final GrainCommodityAttrMapper grainCommodityAttrMapper;

    private final CommodityProductAttrValueService commodityProductAttrValueService;

    private final CommoditySkuInfoService commoditySkuInfoService;

    private final CommoditySkuImagesService commoditySkuImagesService;

    private final GrainCategoryMapper grainCategoryMapper;
    private final GrainBrandMapper grainBrandMapper;

    @Override
    public void saveSpuInfo(SpuSaveVO spuSaveVO) {
        spuProvider.sendSaveSpuMessage(JSONUtil.toJsonStr(spuSaveVO));
    }

    /**
     * 保存商品信息
     * 涉及到多表操作
     *
     * @param spuSaveVO
     */
    @Override
    @Transactional
    public void saveSpuInfo0(SpuSaveVO spuSaveVO) {
        CommoditySpuInfo commoditySpuInfo = new CommoditySpuInfo();
        BeanUtils.copyProperties(spuSaveVO, commoditySpuInfo);
        commoditySpuInfo.setCreateTime(LocalDateTime.now());
        commoditySpuInfo.setUpdateTime(LocalDateTime.now());
        // 保存基本spu信息
        super.save(commoditySpuInfo);
        Long spuId = commoditySpuInfo.getId();
        // 保存商品图片描述信息
        this.saveSpuInfoDesc(spuSaveVO, spuId);
        // 保存商品图片集
        this.saveSpuImages(spuSaveVO, spuId);
        // 保存商品基本属性
        this.saveProductAttr(spuSaveVO, spuId);
        // 保存商品sku信息
        this.saveSkuInfo(spuSaveVO, spuId);

    }

    /**
     * 方法实现
     * @param params 查询条件
     * @return
     */
    @Override
    public PageUtils spuInfoList(Map<String, Object> params) {
        // 提取查询条件
        String status = (String) params.get("status");
        String key = (String) params.get("key");
        String brandId = (String) params.get("brandId");
        String catelogId = (String) params.get("catelogId");
        // 构造查询条件
        LambdaQueryWrapper<CommoditySpuInfo> lambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(key) && !"undefined".equals(key)) {
            lambdaQueryWrapper.eq(CommoditySpuInfo::getId, key)
                    .or()
                    .like(CommoditySpuInfo::getSpuName, key);
        }
        if (StringUtils.isNotBlank(status) && !"undefined".equals(status)) {
            lambdaQueryWrapper.and(wrapper -> wrapper.eq(CommoditySpuInfo::getPublishStatus, status));
        }
        if (StringUtils.isNotBlank(brandId) && !"undefined".equals(brandId)  && !"0".equals(brandId)) {
            lambdaQueryWrapper.and(wrapper -> wrapper.eq(CommoditySpuInfo::getBrandId, brandId));
        }
        if (StringUtils.isNotBlank(catelogId) && !"undefined".equals(catelogId) && !"0".equals(catelogId)) {
            lambdaQueryWrapper.and(wrapper -> wrapper.eq(CommoditySpuInfo::getCatalogId, catelogId));
        }
        IPage<CommoditySpuInfo> page = this.page(
                new Query<CommoditySpuInfo>().getPage(params),
                lambdaQueryWrapper
        );
        // 拿到全部数据
        List<CommoditySpuInfo> records = page.getRecords();
        records.forEach(item -> {
            Long brandId1 = item.getBrandId();
            Long catalogId = item.getCatalogId();
            String categoryName = grainCategoryMapper.selectById(catalogId).getName();
            String brandName = grainBrandMapper.selectById(brandId1).getName();
            item.setCategoryName(categoryName);
            item.setBrandName(brandName);
        });
        return new PageUtils(page);
    }

    /**
     * 产品上架
     */
    @Override
    public void productUp(Long spuId) {
        if (Objects.isNull(spuId) || spuId == 0L) {
            return;
        }
        String time = WebUtil.returnTimeFormat();
        // 上架
        this.baseMapper.updatePublishStatus(spuId, 1, time);
    }

    /**
     * 产品下架
     */
    @Override
    public void productDown(Long spuId) {
        if (Objects.isNull(spuId) || spuId == 0L) {
            return;
        }
        String time = WebUtil.returnTimeFormat();
        // 下架
        this.baseMapper.updatePublishStatus(spuId, 2, time);
    }

    /**
     * 保存商品图片描述信息
     *
     * @param spuSaveVO
     * @param spuId
     */
    protected void saveSpuInfoDesc(SpuSaveVO spuSaveVO, Long spuId) {
        List<String> decript = spuSaveVO.getDecript();
        CommoditySpuInfoDesc spuInfoDesc = new CommoditySpuInfoDesc();
        spuInfoDesc.setSpuId(spuId);
        if (CollectionUtils.isEmpty(decript)) {
            // 设置默认图片
            spuInfoDesc.setDecript("https://zheliving-10000.oss-cn-guangzhou.aliyuncs.com/2024-07-10/5c955558-6a45-4e1e-8c3d-0b2dad2d3725_%E9%99%95%E8%A5%BF%E7%BA%A2%E5%AF%8C%E5%A3%AB%E8%8B%B9%E6%9E%9C.png");
        } else {
            // 将集合分开并且用,间隔
            spuInfoDesc.setDecript(String.join(",", decript));
        }
        commoditySpuInfoDescService.saveSpuDescInfo(spuInfoDesc);
    }

    /**
     * 保存商品图片集
     *
     * @param spuSaveVO
     * @param spuId
     */
    protected void saveSpuImages(SpuSaveVO spuSaveVO, Long spuId) {
        List<String> images = spuSaveVO.getImages();
        commoditySpuImagesService.saveImages(spuId, images);
    }

    /**
     * 构建集合以及批量保存产品基本属性
     *
     * @param spuSaveVO
     * @param spuId
     */
    protected void saveProductAttr(SpuSaveVO spuSaveVO, Long spuId) {
        List<BaseAttrs> baseAttrs = spuSaveVO.getBaseAttrs();
        List<CommodityProductAttrValue> attrValues = baseAttrs.stream()
                .map(baseAttr -> {
                    CommodityProductAttrValue attrValue = new CommodityProductAttrValue();
                    Long attrId = baseAttr.getAttrId();
                    String attrName = grainCommodityAttrMapper.selectById(attrId).getAttrName();
                    attrValue.setSpuId(spuId)
                            .setAttrId(attrId)
                            .setAttrName(attrName)
                            .setAttrValue(baseAttr.getAttrValues())
                            .setQuickShow(baseAttr.getShowDesc());
                    return attrValue;
                }).toList();
        commodityProductAttrValueService.saveBatchProductAttrValue(attrValues);
    }

    /**
     * 批量添加sku商品销售信息
     *
     * @param spuSaveVO
     * @param spuId
     */
    protected void saveSkuInfo(SpuSaveVO spuSaveVO, Long spuId) {
        List<Skus> skus = spuSaveVO.getSkus();
        if (CollectionUtils.isEmpty(skus)) {
            return;
        }
        List<CommoditySkuInfo> skuInfos = skus.stream().map(sku -> {
            CommoditySkuInfo skuInfo = new CommoditySkuInfo();
            sku.getImages().forEach(item -> {
                if (item.getDefaultImg() == 1) {
                    skuInfo.setSkuDefaultImg(item.getImgUrl());
                }
            });
            BeanUtils.copyProperties(sku, skuInfo);
            skuInfo.setSpuId(spuId)
                    .setBrandId(spuSaveVO.getBrandId())
                    .setCatalogId(spuSaveVO.getCatalogId());
            return skuInfo;
        }).toList();
        commoditySkuInfoService.batchSaveSpuInfo(skuInfos);
        this.saveSkuImages(skus, skuInfos);
    }

    /**
     * 一个sku对应多个图片
     *
     * @param skus
     * @param skuInfos
     */
    protected void saveSkuImages(List<Skus> skus, List<CommoditySkuInfo> skuInfos) {
        // 使用原子整型防止高并发造成索引不一致
        AtomicInteger index = new AtomicInteger(0);
        List<CommoditySkuImages> skuImages = new ArrayList<>();
        skus.forEach(sku -> {
            List<Images> images = sku.getImages();
            images.stream()
                    // 过滤掉imageUrl为空的对象
                    .filter(image -> StringUtils.isNotBlank(image.getImgUrl()))
                    .forEach(image -> {
                        CommoditySkuInfo skuInfo = skuInfos.get(index.get());
                        CommoditySkuImages skuImage = new CommoditySkuImages();
                        skuImage.setSkuId(skuInfo.getSkuId())
                                .setImgUrl(image.getImgUrl())
                                .setDefaultImg(image.getDefaultImg());
                        skuImages.add(skuImage);
                    });
            index.getAndIncrement();
        });
        commoditySkuImagesService.saveBatchSkuImages(skuImages);
    }
}
