package com.zhe.grain.service.commodity;

import com.zhe.grain.entity.CommoditySpuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu 图片集 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-11
 */
public interface CommoditySpuImagesService extends IService<CommoditySpuImages> {

    /**
     * 批量保存
     * @param spuId
     * @param images
     */
    void saveImages(Long spuId, List<String> images);
}
