package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.service.commodity.CommoditySkuInfoService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * sku 信息 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-13
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "skuInfo")
public class CommoditySkuInfoController {
    private final CommoditySkuInfoService commoditySkuInfoService;

    @Autowired
    public CommoditySkuInfoController(CommoditySkuInfoService commoditySkuInfoService) {
        this.commoditySkuInfoService = commoditySkuInfoService;
    }

    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAuthority('sys:sku:list')")
    public Result<PageUtils> skuInfoList(@RequestParam Map<String, Object> params) {
        PageUtils page = commoditySkuInfoService.skuInfoList(params);
        return Result.success(page);
    }
}
