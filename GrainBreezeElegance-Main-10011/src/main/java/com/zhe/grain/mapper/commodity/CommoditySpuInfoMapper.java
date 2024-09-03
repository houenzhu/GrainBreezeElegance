package com.zhe.grain.mapper.commodity;

import com.zhe.grain.domain.commodity.CommoditySpuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品spu信息 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
public interface CommoditySpuInfoMapper extends BaseMapper<CommoditySpuInfo> {

    /**
     * 处理上架或者下架
     */
    void updatePublishStatus(@Param("spuId") Long spuId,
                             @Param("publishStatus") Integer publishStatus,
                             @Param("time") String time);
}
