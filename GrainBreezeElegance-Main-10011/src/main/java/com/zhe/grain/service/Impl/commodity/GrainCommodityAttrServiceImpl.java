package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.entity.*;
import com.zhe.grain.mapper.commodity.*;
import com.zhe.grain.service.commodity.GrainCommodityAttrAttrgroupRelationService;
import com.zhe.grain.service.commodity.GrainCommodityAttrService;
import com.zhe.grain.service.commodity.GrainSaleAttrBrandRelationService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.utils.ReflectUtils;
import com.zhe.grain.vo.BaseAttrAcceptVO;
import com.zhe.grain.vo.BaseAttrFormVO;
import com.zhe.grain.vo.GrainCommodityAttrVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-08
 */
@Service
@Slf4j
public class GrainCommodityAttrServiceImpl extends ServiceImpl<GrainCommodityAttrMapper, GrainCommodityAttr> implements GrainCommodityAttrService {

    @Autowired
    private GrainCommodityAttrMapper grainCommodityAttrMapper;

    @Autowired
    private GrainCategoryMapper grainCategoryMapper;

    @Autowired
    private GrainAttrCategoryRelationMapper grainAttrCategoryRelationMapper;

    @Autowired
    private GrainCommodityAttrAttrgroupRelationMapper attrAttrgroupRelationMapper;

    @Autowired
    private GrainCommodityAttrgroupMapper grainCommodityAttrgroupMapper;

    @Autowired
    private GrainCommodityAttrAttrgroupRelationService grainCommodityAttrAttrgroupRelationService;

    @Autowired
    private GrainSaleAttrBrandRelationService grainSaleAttrBrandRelationService;

    @Autowired
    private GrainSaleAttrBrandRelationMapper grainSaleAttrBrandRelationMapper;

    @Autowired
    private GrainBrandMapper grainBrandMapper;


    /**
     * 分页显示基本属性信息的实现
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<GrainCommodityAttr> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        // 可以筛选关键字(id|name)
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq("attr_id", key).or().like("attr_name", key);
        }
        IPage<GrainCommodityAttr> page = this.page(
                new Query<GrainCommodityAttr>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<GrainCommodityAttrVO> list = this.grainCommodityAttrToGrainCommodityAttrVO(pageUtils);
        pageUtils.setList(list);
        return pageUtils;
    }

    /**
     * 添加商品属性的实现
     *
     * @param attrFormVO
     */
    @Override
    @Transactional
    public void saveAttr(BaseAttrFormVO attrFormVO) {
        this.saveAttr1(attrFormVO);
    }

    /**
     * 批量删除商品属性的实现
     *
     * @param attrIds
     */
    @Override
    @Transactional
    public void batchDeleteAttr(List<Long> attrIds) {
        new Thread(() -> batchDeleteAttr0(attrIds)).start();
    }

    /**
     * 返回attrId对应的属性信息
     * @param attrId
     * @return
     */
    @Override
    public GrainCommodityAttrVO getAttrInfo(Long attrId) {
        if (null == attrId) {
            return null;
        }
        GrainCommodityAttr grainCommodityAttr = this.baseMapper.selectById(attrId);
        // 构造一个基本的属性
        Map<String, Object> values = constructMap(grainCommodityAttr);
        // 构造分类层级返回给前端
        List<Long> ids = new ArrayList<>();
        Long categoryId = grainCommodityAttr.getCategoryId();
        ids.add(categoryId);
        GrainCategoryEntity categoryEntity = grainCategoryMapper.selectById(categoryId);
        if (categoryEntity.getParentId() != 0) {
            ids.add(categoryEntity.getParentId());
        }
        // 将分类好的id反转, 形成层级关系
        Collections.reverse(ids);
        GrainCommodityAttrVO grainCommodityAttrVO;
        try {
            grainCommodityAttrVO = (GrainCommodityAttrVO)
                    ReflectUtils.reflectObject(GrainCommodityAttrVO.class, values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        grainCommodityAttrVO.setCategoryIds(ids);
        // 如果是基本属性, 则需要查询属性分组信息
        if (grainCommodityAttr.getAttrType() == 1) {
            // 通过attrId查询到关联表的信息
            GrainCommodityAttrAttrgroupRelation relation = attrAttrgroupRelationMapper.selectOne(
                    new QueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                            .eq("attr_id", attrId)
            );

            if (Objects.isNull(relation)) {
                grainCommodityAttrVO.setAttrGroupId(null);
            } else {
                // 通过关联表查到属性分组的id再通过id查到属性分组的名称
                GrainCommodityAttrgroup grainCommodityAttrgroup = grainCommodityAttrgroupMapper.selectById(relation.getAttrGroupId());
                grainCommodityAttrVO.setAttrGroupId(grainCommodityAttrgroup.getId());
            }
        } else { // 若是销售属性, 则需要查询对应的品牌id
            GrainSaleAttrBrandRelation attrBrandRelation = grainSaleAttrBrandRelationService.getBaseMapper()
                    .selectOne(
                            new LambdaQueryWrapper<GrainSaleAttrBrandRelation>()
                                    .eq(GrainSaleAttrBrandRelation::getSaleAttrId, attrId)
                    );
            if (Objects.isNull(attrBrandRelation)) {
                grainCommodityAttrVO.setBrandId(null);
            } else {
                grainCommodityAttrVO.setBrandId(attrBrandRelation.getBrandId());
            }
        }
        return grainCommodityAttrVO;
    }

    /**
     * 更新操作
     *
     * @param attrFormVO
     * @return
     */
    @Override
    @Transactional
    public boolean updateAttr(@NotNull BaseAttrAcceptVO attrFormVO) {
        Long attrId = attrFormVO.getAttrId();
        Long attrGroupId = attrFormVO.getAttrGroupId();
        Long brandId = attrFormVO.getBrandId();
        GrainCommodityAttr grainCommodityAttr = new GrainCommodityAttr();
        grainCommodityAttr.setAttrId(attrId)
                .setAttrType(attrFormVO.getAttrType())
                .setAttrName(attrFormVO.getAttrName())
                .setEnable(attrFormVO.getEnable())
                .setValueSelect(attrFormVO.getValueSelect())
                .setCategoryId(attrFormVO.getCategoryId());
        // 如果是更新成为销售属性，则需要将基本属性关联的属性分组删除(销售属性不需要跟任何的属性分组绑定)
        if (attrFormVO.getAttrType() == 0) {
            attrAttrgroupRelationMapper.delete(
                    new QueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                            .eq("attr_id", attrId)
            );
        }
        // 执行更新操作
        this.baseMapper.updateById(grainCommodityAttr);
        GrainCommodityAttrAttrgroupRelation relation = new GrainCommodityAttrAttrgroupRelation();
        if (attrFormVO.getAttrType() == 1) {
            // 如果改成基本属性, 则需要把销售属性和品牌关联关系删除
            grainSaleAttrBrandRelationMapper.delete(
                    new QueryWrapper<GrainSaleAttrBrandRelation>()
                            .eq("sale_attr_id", attrId)
            );
            relation.setAttrId(attrId)
                    .setAttrGroupId(attrGroupId);
            GrainCommodityAttrAttrgroupRelation exist = attrAttrgroupRelationMapper.selectOne(
                    new QueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                            .eq("attr_id", attrId)
            );
            // 存在就更新, 否则就新增
            if (!Objects.isNull(exist)) {
                relation.setId(exist.getId());
                attrAttrgroupRelationMapper.updateById(relation);
            } else {
                attrAttrgroupRelationMapper.insert(relation);
            }
        } else { // 修改销售属性与品牌关联
            // 如果改成销售属性, 则需要把基本属性和属性分组关联关系删除
            attrAttrgroupRelationMapper.delete(
                    new QueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                            .eq("attr_id", attrId)
            );
            GrainSaleAttrBrandRelation saleAttrBrandRelation =
                    new GrainSaleAttrBrandRelation();
            String brandName = grainBrandMapper.selectById(brandId).getName();
            saleAttrBrandRelation.setBrandId(brandId)
                    .setSaleAttrId(attrId)
                    .setBrandName(brandName);
            GrainSaleAttrBrandRelation one = grainSaleAttrBrandRelationService.getOne(
                    new LambdaQueryWrapper<GrainSaleAttrBrandRelation>()
                            .eq(GrainSaleAttrBrandRelation::getSaleAttrId, attrId)
            );
            // 存在则更新, 不存在则新增
            if (Objects.isNull(one)) {
                grainSaleAttrBrandRelationService.save(saleAttrBrandRelation);
            } else {
                saleAttrBrandRelation.setId(one.getId());
                grainSaleAttrBrandRelationService.updateById(saleAttrBrandRelation);
            }

        }
        return true;
    }

    /**
     * 通过查询属性分组的id返回所有关联到该分组的所有的商品基本属性的信息
     *
     * @param attrGroupId 属性分组id
     * @return List<GrainCommodityAttr>
     */
    @Override
    public List<GrainCommodityAttr> selectByAttrGroupId(Long attrGroupId) {
        if (Objects.isNull(attrGroupId)) {
            return null;
        }
        // 先拿到所有的关联信息
        List<GrainCommodityAttrAttrgroupRelation> relations = attrAttrgroupRelationMapper.selectList(
                new QueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                        .eq("attr_group_id", attrGroupId)
        );
        // 将获取到的attrId变成集合
        List<Long> attrIds = relations.stream()
                .map(GrainCommodityAttrAttrgroupRelation::getAttrId)
                .toList();
        if (CollectionUtils.isEmpty(attrIds)) {
            return null;
        }
        return this.baseMapper.selectByAttrIds(attrIds);
    }

    /**
     * 分页加载还没关联的商品属性分组
     *
     * @param attrId 属性分组id
     * @return
     */
    @Override
    public PageUtils selectNotInAttrIds(Map<String, Object> params, Long attrId) {
        if (Objects.isNull(attrId)) {
            return null;
        }
        // 关键词
        String key = (String) params.get("key");
        List<GrainCommodityAttrAttrgroupRelation> relations = attrAttrgroupRelationMapper.selectList(
                new LambdaQueryWrapper<GrainCommodityAttrAttrgroupRelation>()
                        .eq(GrainCommodityAttrAttrgroupRelation::getAttrGroupId, attrId)
        );
        // 查询还没有关联的所有基本属性
        List<Long> ids = relations.stream()
                .map(GrainCommodityAttrAttrgroupRelation::getAttrId)
                .toList();
        // 构造查询条件
        LambdaQueryWrapper<GrainCommodityAttr> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!CollectionUtils.isEmpty(ids)) {
            lambdaQueryWrapper.notIn(GrainCommodityAttr::getAttrId, ids);
        }
        if (StringUtils.isNotBlank(key)) {
            lambdaQueryWrapper.and(wrapper ->
                    wrapper.eq(GrainCommodityAttr::getAttrId, key)
                            .or()
                            .like(GrainCommodityAttr::getAttrName, key)
            );
        }
        lambdaQueryWrapper.eq(GrainCommodityAttr::getAttrType, 1);
        // 构造分页
        IPage<GrainCommodityAttr> page = this.page(
                new Query<GrainCommodityAttr>().getPage(params),
                lambdaQueryWrapper
        );
        return new PageUtils(page);
    }

    /**
     * 根据分类id及品牌id查询出所有销售属性
     * @param categoryId
     * @param brandId
     * @return
     */
    @Override
    public List<GrainCommodityAttr> selectSaleAttrByCategoryId(Long categoryId, Long brandId) {
        if (Objects.isNull(categoryId) || Objects.isNull(brandId)) {
            return null;
        }
        // 通过品牌id查询到所有关联的销售属性, 是个集合, 一对多的关系
        List<GrainSaleAttrBrandRelation> attrBrandRelations = grainSaleAttrBrandRelationMapper.selectList(
                new LambdaQueryWrapper<GrainSaleAttrBrandRelation>()
                        .eq(GrainSaleAttrBrandRelation::getBrandId, brandId)
        );
        // 收集所有销售属性的id, 一边做联合查询
        List<Long> attrIds = attrBrandRelations.stream()
                .map(GrainSaleAttrBrandRelation::getSaleAttrId).toList();
        // 需要的是销售属性, 所以需要attrType=0
        // 相当于sql语句
        // select * from grain_commodity_attr where attr_id in (?, ?, ...) and category_id = ? and attr_type = ?
        return this.baseMapper.selectList(
                new LambdaQueryWrapper<GrainCommodityAttr>()
                        .in(GrainCommodityAttr::getAttrId, attrIds)
                        .and(wrapper -> wrapper.eq(GrainCommodityAttr::getCategoryId, categoryId))
                        .and(wrapper -> wrapper.eq(GrainCommodityAttr::getAttrType, 0))
        );
    }

    @Transactional
    public void batchDeleteAttr0(List<Long> attrIds) {
        grainCommodityAttrMapper.deleteBatchIds(attrIds);
        attrAttrgroupRelationMapper.batchDeleteAttrId(attrIds);
        grainSaleAttrBrandRelationMapper.batchDeleteSaleAttrBrandRelation(attrIds);
    }

    // 涉及到多张表的操作需要使用事务
    @Transactional
    @Deprecated(since = "2024-7-4")
    public void saveAttr0(BaseAttrFormVO attrFormVO) {
        // 创建和构建商品属性对象
        GrainCommodityAttr grainCommodityAttr = new GrainCommodityAttr();
        grainCommodityAttr.setAttrType(attrFormVO.getAttrType())
                .setAttrName(attrFormVO.getAttrName())
                .setEnable(1L)
                .setValueSelect(attrFormVO.getValueSelect());
        grainCommodityAttrMapper.insert(grainCommodityAttr);
        // 添加商品属性和分类关联
        GrainAttrCategoryRelation categoryRelation;
        Long[] categoryIds = attrFormVO.getCategoryIds();
        for (Long categoryId : categoryIds) {
            categoryRelation = new GrainAttrCategoryRelation();
            categoryRelation.setAttrId(grainCommodityAttr.getAttrId());
            categoryRelation.setCategoryId(categoryId);
            grainAttrCategoryRelationMapper.insert(categoryRelation);
        }
        // 添加商品属性和属性分组关联
        GrainCommodityAttrAttrgroupRelation relation =
                new GrainCommodityAttrAttrgroupRelation();
        relation.setAttrId(grainCommodityAttr.getAttrId())
                .setAttrGroupId(attrFormVO.getAttrGroupId());
        attrAttrgroupRelationMapper.insert(relation);
    }

    // 涉及到多张表的操作需要使用事务
    @Transactional
    public void saveAttr1(BaseAttrFormVO attrFormVO) {
        // 创建和构建商品属性对象(只添加商品的基本属性, 销售属性不添加进分组)
        GrainCommodityAttr grainCommodityAttr = new GrainCommodityAttr();
        grainCommodityAttr.setAttrType(attrFormVO.getAttrType())
                .setAttrName(attrFormVO.getAttrName())
                .setEnable(1L)
                .setValueSelect(attrFormVO.getValueSelect())
                .setCategoryId(attrFormVO.getCategoryIds()[0]);
        grainCommodityAttrMapper.insert(grainCommodityAttr);
        Long attrId = grainCommodityAttr.getAttrId();
        Long attrGroupId = attrFormVO.getAttrGroupId();
        Long brandId = attrFormVO.getBrandId();
        if (attrFormVO.getAttrType() == 1) { // 添加的是基本属性,就要跟属性分组关联
            this.saveBaseAttr(attrId, attrGroupId);
        } else {
            this.saveSaleAttrWithBrand(brandId, attrId);
        }
    }

    /**
     * 添加基本属性和属性分组的关联
     */
    @Transactional
    public void saveBaseAttr(Long attrId, Long attrGroupId) {
        GrainCommodityAttrAttrgroupRelation relation =
                new GrainCommodityAttrAttrgroupRelation();
        relation.setAttrGroupId(attrGroupId)
                .setAttrId(attrId);
        log.info("构建好的对象 ---> {}", relation);
        attrAttrgroupRelationMapper.insert(relation);
    }

    /**
     * 添加销售属性和品牌关联
     *
     * @param brandId
     */
    @Transactional
    public void saveSaleAttrWithBrand(Long brandId, Long attrId) {
        grainSaleAttrBrandRelationService.saveSaleAttrBrandRelation(brandId, attrId);
    }


    private List<GrainCommodityAttrVO> grainCommodityAttrToGrainCommodityAttrVO(PageUtils pageUtils) {
        return pageUtils.getList().stream().map(item -> {
            GrainCommodityAttr grainCommodityAttr = (GrainCommodityAttr) item;
            Map<String, Object> values = constructMap(grainCommodityAttr);
            // 先通过商品属性id查询关联到的所有分类的id
            String name = grainCategoryMapper
                    .selectById(grainCommodityAttr.getCategoryId())
                    .getName();
            values.put("categoryName", name);
            GrainCommodityAttrVO grainCommodityAttrVO;
            try {
                grainCommodityAttrVO = (GrainCommodityAttrVO) ReflectUtils
                        .reflectObject(GrainCommodityAttrVO.class, values);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return grainCommodityAttrVO;
        }).toList();
    }

    private Map<String, Object> constructMap(GrainCommodityAttr grainCommodityAttr) {
        Map<String, Object> values = new HashMap<>();
        values.put("attrId", grainCommodityAttr.getAttrId());
        values.put("attrName", grainCommodityAttr.getAttrName());
        values.put("valueSelect", grainCommodityAttr.getValueSelect());
        values.put("enable", grainCommodityAttr.getEnable());
        values.put("attrType", grainCommodityAttr.getAttrType());
        return values;
    }

}
