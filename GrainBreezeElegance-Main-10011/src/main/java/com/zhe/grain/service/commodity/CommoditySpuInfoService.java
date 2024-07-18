package com.zhe.grain.service.commodity;

import com.zhe.grain.entity.CommoditySpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.vo.SpuSaveVO;

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
}
