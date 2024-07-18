package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.entity.*;
import com.zhe.grain.mapper.commodity.*;
import com.zhe.grain.service.commodity.GrainCategoryService;
import com.zhe.grain.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@Slf4j
public class GrainCategoryServiceImpl extends ServiceImpl<GrainCategoryMapper, GrainCategoryEntity>
        implements GrainCategoryService {

    private final GrainCategoryMapper grainCategoryMapper;

    private final RedisCache redisCache;
    private final GrainCategoryBrandRelationMapper grainCategoryBrandRelationMapper;
    private final GrainCommodityAttrgroupCategoryRelationMapper attrgroupCategoryRelationMapper;

    private final GrainCommodityAttrMapper grainCommodityAttrMapper;
    private final GrainSaleAttrBrandRelationMapper grainSaleAttrBrandRelationMapper;
    private final CommoditySpuInfoMapper commoditySpuInfoMapper;
    private final CommoditySkuInfoMapper commoditySkuInfoMapper;
    private final GrainCommodityAttrAttrgroupRelationMapper attrAttrgroupRelationMapper;
    private final CommoditySpuImagesMapper commoditySpuImagesMapper;
    private final CommoditySpuInfoDescMapper commoditySpuInfoDescMapper;
    private final CommoditySkuImagesMapper commoditySkuImagesMapper;

    // 使用构造器注入
    @Autowired
    public GrainCategoryServiceImpl(GrainCategoryMapper grainCategoryMapper,
                                    RedisCache redisCache,
                                    GrainCategoryBrandRelationMapper grainCategoryBrandRelationMapper,
                                    GrainCommodityAttrgroupCategoryRelationMapper attrgroupCategoryRelationMapper,
                                    GrainCommodityAttrMapper grainCommodityAttrMapper,
                                    GrainSaleAttrBrandRelationMapper grainSaleAttrBrandRelationMapper,
                                    CommoditySpuInfoMapper commoditySpuInfoMapper,
                                    CommoditySkuInfoMapper commoditySkuInfoMapper,
                                    GrainCommodityAttrAttrgroupRelationMapper attrAttrgroupRelationMapper,
                                    CommoditySpuImagesMapper commoditySpuImagesMapper,
                                    CommoditySpuInfoDescMapper commoditySpuInfoDescMapper,
                                    CommoditySkuImagesMapper commoditySkuImagesMapper) {
        this.grainCategoryMapper = grainCategoryMapper;
        this.redisCache = redisCache;
        this.grainCategoryBrandRelationMapper = grainCategoryBrandRelationMapper;
        this.attrgroupCategoryRelationMapper = attrgroupCategoryRelationMapper;
        this.grainCommodityAttrMapper = grainCommodityAttrMapper;
        this.grainSaleAttrBrandRelationMapper = grainSaleAttrBrandRelationMapper;
        this.commoditySpuInfoMapper = commoditySpuInfoMapper;
        this.commoditySkuInfoMapper = commoditySkuInfoMapper;
        this.attrAttrgroupRelationMapper = attrAttrgroupRelationMapper;
        this.commoditySpuImagesMapper = commoditySpuImagesMapper;
        this.commoditySpuInfoDescMapper = commoditySpuInfoDescMapper;
        this.commoditySkuImagesMapper = commoditySkuImagesMapper;
    }

    /**
     * 获取树形商品分类列表
     *
     * @return
     */
    @Override
    public List<GrainCategoryEntity> getCategoryList() {
        List<GrainCategoryEntity> listTree = redisCache.getCacheObject(RedisConstant.CATEGORY_LIST_TREE);
        if (CollectionUtils.isEmpty(listTree)) {
            List<GrainCategoryEntity> all = grainCategoryMapper.selectList(null);
            listTree = all.stream()
                    .filter(categoryEntity -> categoryEntity.getParentId().equals(0L))
                    .map(grainCategoryEntity -> grainCategoryEntity.setChildren(this.getChildrenByParentId(all, grainCategoryEntity)))
                    // 升序排序
                    .sorted((category1, category2) -> (category1.getSort() == null ? 0 : category1.getSort())
                            - (category2.getSort() == null ? 0 : category2.getSort())
                    )
                    .collect(Collectors.toList());
            redisCache.setCacheObject(RedisConstant.CATEGORY_LIST_TREE, listTree);
        }
        return listTree;
    }

    /**
     * 添加节点
     *
     * @param grainCategoryEntity
     */
    @Override
    public void addNode(GrainCategoryEntity grainCategoryEntity) {
        if (grainCategoryEntity.getParentId() == null) {
            return;
        }
        grainCategoryEntity.setSort(1);
        if (StringUtils.isBlank(grainCategoryEntity.getIcon())) {
            grainCategoryEntity.setIcon("");
        }
        grainCategoryEntity.setProCount(0);
        new Thread(() -> {
            grainCategoryMapper.insert(grainCategoryEntity);
            // 新增完结点后需要让redis和mysql进行数据同步
            redisCache.deleteObject(RedisConstant.CATEGORY_LIST_TREE);
        }).start();
    }

    /**
     * 构造子节点
     *
     * @param all  所有的数据
     * @param root 根节点
     */
    @Override
    public List<GrainCategoryEntity> getChildrenByParentId(List<GrainCategoryEntity> all, GrainCategoryEntity root) {
        List<GrainCategoryEntity> childrenTree = all.stream()
                .filter(grainCategoryEntity -> grainCategoryEntity.getParentId().equals(root.getId()))
                .map(grainCategoryEntity -> {
                    // 给每个分类设置子分类, 每个子分类都会变为根节点递归查找子节点
                    return grainCategoryEntity
                            .setChildren(getChildrenByParentId(all, grainCategoryEntity));
                })
                .sorted((category1, category2) -> (category1.getSort() == null ? 0 : category1.getSort())
                        - (category2.getSort() == null ? 0 : category2.getSort())
                )
                .collect(Collectors.toList());
        return childrenTree;
    }

    /**
     * 对删除结点进行级联删除
     *
     * @param ids 选中的结点id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteNodes(List<Long> ids) {
        try {
            // 删除本表的结点(包含子节点)
            this.removeByIds(ids);
            redisCache.deleteObject(RedisConstant.CATEGORY_LIST_TREE);
            // 拿到所有分类id的属性集合
            List<GrainCommodityAttr> grainCommodityAttrs = grainCommodityAttrMapper.selectList(
                    new LambdaQueryWrapper<GrainCommodityAttr>()
                            .in(GrainCommodityAttr::getCategoryId, ids)
            );
            grainCommodityAttrMapper.delete(
                    new LambdaQueryWrapper<GrainCommodityAttr>()
                            .in(GrainCommodityAttr::getCategoryId, ids)
            );
            // 删除分类与品牌关联
            grainCategoryBrandRelationMapper.delete(
                    new LambdaQueryWrapper<GrainCategoryBrandRelation>()
                            .in(GrainCategoryBrandRelation::getCategoryId, ids)
            );
            // 删除分类与属性组的关联关系
            attrgroupCategoryRelationMapper.delete(
                    new LambdaQueryWrapper<GrainCommodityAttrgroupCategoryRelation>()
                            .in(GrainCommodityAttrgroupCategoryRelation::getCategoryId, ids)
            );
            // 删除销售属性(如果一个销售属性都脱离了品牌了,就没有保留的意义了)
            // 获取销售属性id
            List<Long> saleIds = grainCommodityAttrs.stream()
                    .filter(grainCommodityAttr -> grainCommodityAttr.getAttrType() == 0)
                    .map(GrainCommodityAttr::getAttrId)
                    .toList();
            // 获取基本属性id
            List<Long> attrIds = grainCommodityAttrs.stream()
                    .filter(grainCommodityAttr -> grainCommodityAttr.getAttrType() == 1)
                    .map(GrainCommodityAttr::getAttrId)
                    .toList();
            grainSaleAttrBrandRelationMapper.delete(
                    new LambdaQueryWrapper<GrainSaleAttrBrandRelation>()
                            .in(GrainSaleAttrBrandRelation::getSaleAttrId, saleIds)
            );
            // 删除基本属性与属性组之间的关联
            attrAttrgroupRelationMapper.delete(
                    new LambdaQueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                            .in(GrainCommodityAttrAttrgroupRelation::getAttrId, attrIds)
            );
            removeSpuAndSkuRelation(ids);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException("异常!!!");
        }
    }

    /**
     * <p>删除spu、sku与分类有关系的所有表的数据</p>
     * @param categoryIds 分类id
     */
    protected void removeSpuAndSkuRelation(List<Long> categoryIds) {
        List<CommoditySpuInfo> commoditySpuInfos = commoditySpuInfoMapper.selectList(
                new LambdaQueryWrapper<CommoditySpuInfo>()
                        .in(CommoditySpuInfo::getCatalogId, categoryIds)
        );
        List<Long> spuIds = commoditySpuInfos.stream()
                .map(CommoditySpuInfo::getId)
                .toList();
        List<Long> skuIds = commoditySkuInfoMapper.selectList(
                new LambdaQueryWrapper<CommoditySkuInfo>()
                        .in(CommoditySkuInfo::getCatalogId, categoryIds)
        ).stream().map(CommoditySkuInfo::getSkuId).toList();
        // 将商品发布后的spu和sku信息也删除
        commoditySpuInfoMapper.delete(
                new LambdaQueryWrapper<CommoditySpuInfo>()
                        .in(CommoditySpuInfo::getCatalogId, categoryIds)
        );
        commoditySkuInfoMapper.delete(
                new LambdaQueryWrapper<CommoditySkuInfo>()
                        .in(CommoditySkuInfo::getCatalogId, categoryIds)
        );
        commoditySpuInfoDescMapper.delete(
                new LambdaQueryWrapper<CommoditySpuInfoDesc>()
                        .in(CommoditySpuInfoDesc::getSpuId, spuIds)
        );
        commoditySpuImagesMapper.delete(
                new LambdaQueryWrapper<CommoditySpuImages>()
                        .in(CommoditySpuImages::getSpuId, spuIds)
        );
        commoditySkuImagesMapper.delete(
                new LambdaQueryWrapper<CommoditySkuImages>()
                        .in(CommoditySkuImages::getSkuId, skuIds)
        );
    }
}
