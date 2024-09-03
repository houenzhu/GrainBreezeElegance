package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.vo.commodity.BaseAttrAcceptVO;
import com.zhe.grain.vo.commodity.BaseAttrFormVO;
import com.zhe.grain.vo.commodity.GrainCommodityAttrVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-08
 */
public interface GrainCommodityAttrService extends IService<GrainCommodityAttr> {

    /**
     * 分页显示基本属性信息
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加商品属性
     * @param attrFormVO
     */
    void saveAttr(BaseAttrFormVO attrFormVO);

    /**
     * 批量删除商品属性
     * @param attrIds
     */
    void batchDeleteAttr(List<Long> attrIds);

    /**
     * 返回attrId对应的属性信息
     */
    GrainCommodityAttrVO getAttrInfo(Long attrId);

    boolean updateAttr(BaseAttrAcceptVO attrFormVO);

    /**
     * 通过查询属性分组的id返回所有关联到该分组的所有的商品基本属性的信息
     * @param attrGroupId
     * @return
     */
    List<GrainCommodityAttr> selectByAttrGroupId(Long attrGroupId);

    /**
     * 分页加载还没关联的商品属性分组
     * @param attrId 属性分组id
     * @return
     */
    PageUtils selectNotInAttrIds(Map<String, Object> params, Long attrId);

    /**
     * 根据分类id及品牌id查询出所有销售属性
     * @param categoryId 所属分类id
     * @param brandId 分类下对应的品牌id
     * @return
     */
    List<GrainCommodityAttr> selectSaleAttrByCategoryId(Long categoryId, Long brandId);
}
