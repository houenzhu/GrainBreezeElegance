package com.zhe.grain.api.commodity;

import com.zhe.grain.service.commodity.CommoditySpuInfoService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.commodity.SpuSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    private final CommoditySpuInfoService commoditySpuInfoService;

    @Autowired
    public CommoditySpuInfoController(CommoditySpuInfoService commoditySpuInfoService) {
        this.commoditySpuInfoService = commoditySpuInfoService;
    }

    /**
     * 保存商品spu及sku属性
     * @param spuSaveVO
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("@zhe.hasAuthority('sys:spu:save')")
    public Result<Object> saveSpuInfo(@RequestBody SpuSaveVO spuSaveVO) {
        commoditySpuInfoService.saveSpuInfo(spuSaveVO);
        return Result.success();
    }

    /**
     * 查询商品spu列表(分页 + 条件)
     * @param params
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAuthority('sys:spu:list')")
    public Result<PageUtils> spuInfoList(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = commoditySpuInfoService.spuInfoList(params);
        return Result.success(pageUtils);
    }

    /**
     * 商品上架
     */
    @PostMapping("/{spuId}/up")
    @PreAuthorize("@zhe.hasAuthority('sys:spu:up')")
    public Result<Boolean> productUp(@PathVariable Long spuId) {
        commoditySpuInfoService.productUp(spuId);
        return Result.success("上架成功, 请稍等片刻更新", null);
    }

    /**
     * 商品下架
     */
    @PostMapping("/{spuId}/down")
    @PreAuthorize("@zhe.hasAuthority('sys:spu:down')")
    public Result<Boolean> productDown(@PathVariable Long spuId) {
        commoditySpuInfoService.productDown(spuId);
        return Result.success("下架成功, 请稍等片刻更新", null);
    }
}
