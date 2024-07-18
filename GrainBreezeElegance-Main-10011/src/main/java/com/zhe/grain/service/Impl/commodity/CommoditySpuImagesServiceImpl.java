package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.entity.CommoditySpuImages;
import com.zhe.grain.mapper.commodity.CommoditySpuImagesMapper;
import com.zhe.grain.service.commodity.CommoditySpuImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * spu 图片集 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-11
 */
@Service
public class CommoditySpuImagesServiceImpl extends ServiceImpl<CommoditySpuImagesMapper, CommoditySpuImages> implements CommoditySpuImagesService {

    /**
     * 批量保存SPU对应的图片集
     * @param spuId
     * @param images
     */
    @Override
    public void saveImages(Long spuId, List<String> images) {
        if (CollectionUtils.isEmpty(images)) {
            CommoditySpuImages commoditySpuImages = new CommoditySpuImages();
            commoditySpuImages.setSpuId(spuId);
            commoditySpuImages.setImgUrl("default.jpg");
            commoditySpuImages.setDefaultImg(1);
            this.save(commoditySpuImages);
        } else {
            List<CommoditySpuImages> imagesList = images.stream()
                    .map(image -> {
                        CommoditySpuImages commoditySpuImages = new CommoditySpuImages();
                        commoditySpuImages.setSpuId(spuId);
                        commoditySpuImages.setImgUrl(image);
                        return commoditySpuImages;
                    }).toList();
            this.saveBatch(imagesList);
        }

    }
}
