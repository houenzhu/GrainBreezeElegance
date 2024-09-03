package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.CommoditySpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.vo.commodity.SpuSaveVO;

import java.util.Map;

/**
 * <p>
 * 商品spu信息 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
public interface CommoditySpuInfoService extends IService<CommoditySpuInfo> {

    /**
     * 发送消息到消息队列
     * @param spuSaveVO
     */
    void saveSpuInfo(SpuSaveVO spuSaveVO);

    /**
     * 真正保存spu基本信息
     * @param spuSaveVO
     */
    void saveSpuInfo0(SpuSaveVO spuSaveVO);

    /**
     * 查询spu信息
     * @param params 查询条件
     * @return spu分页信息集合
     */
    PageUtils spuInfoList(Map<String, Object> params);

    /**
     * 产品上架
     */
    void productUp(Long spuId);

    /**
     * 产品下架
     * @param spuId
     */
    void productDown(Long spuId);
}
