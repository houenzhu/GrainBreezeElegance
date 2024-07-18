package com.zhe.grain.controller.commodity;

import com.zhe.grain.service.commodity.CommoditySpuInfoService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.SpuSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品spu信息 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
@RestController
@RequestMapping("/grain/spuInfo")
public class CommoditySpuInfoController {

    @Autowired
    private CommoditySpuInfoService commoditySpuInfoService;

    /**
     * 保存商品spu及sku属性
     * @param spuSaveVO
     * @return
     */
    @PostMapping("/save")
    public Result<Object> saveSpuInfo(@RequestBody SpuSaveVO spuSaveVO) {
        commoditySpuInfoService.saveSpuInfo(spuSaveVO);
        return Result.success();
    }
}
