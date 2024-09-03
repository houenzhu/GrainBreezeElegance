package com.zhe.grain.api.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttr;
import com.zhe.grain.service.commodity.GrainCommodityAttrService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.commodity.BaseAttrAcceptVO;
import com.zhe.grain.vo.commodity.BaseAttrFormVO;
import com.zhe.grain.vo.commodity.GrainCommodityAttrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 商品属性表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-08
 */
@RestController
@RequestMapping("/grain/grainCommodityAttr")
public class GrainCommodityAttrController {

    @Autowired
    private GrainCommodityAttrService grainCommodityAttrService;

    /**
     * 分页查询基本属性
     * @param params
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAuthority('sys:grain_commodity_attr:list')")
    public Result<PageUtils> queryPage(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = grainCommodityAttrService.queryPage(params);
        if (Objects.isNull(pageUtils)) {
            return Result.error();
        }
        return Result.success(pageUtils);
    }

    /**
     * 添加基本属性
     */
    @PostMapping("/saveBaseAttr")
    @PreAuthorize("@zhe.hasAuthority('sys:grain_commodity_attr:save')")
    public Result<Object> saveBaseAttr(@RequestBody BaseAttrFormVO baseAttrFormVO) {
        grainCommodityAttrService.saveAttr(baseAttrFormVO);
        return Result.success();
    }
    /**
     * 批量删除基本属性
     */
    @PostMapping("/remove")
    @PreAuthorize("@zhe.hasAuthority('sys:grain_commodity_attr:remove')")
    public Result<Object> remove(@RequestBody List<Long> attrIds) {
        grainCommodityAttrService.batchDeleteAttr(attrIds);
        return Result.success("批量删除操作开始...");
    }

    /**
     * 通过attrId查找信息
     */
    @GetMapping("/getInfo/{attrId}")
    @PreAuthorize("@zhe.hasAuthority('sys:grain_commodity_attr:info')")
    public Result<GrainCommodityAttrVO> getInfo(@PathVariable("attrId") Long attrId) {
        GrainCommodityAttrVO attrInfo = grainCommodityAttrService.getAttrInfo(attrId);
        if (Objects.isNull(attrInfo)) {
            return Result.error();
        }
        return Result.success(attrInfo);
    }


    /**
     * 修改基本属性
     */
    @PostMapping("/update")
    @PreAuthorize("@zhe.hasAuthority('sys:grain_commodity_attr:update')")
    public Result<Object> updateBaseAttr(@RequestBody BaseAttrAcceptVO baseAttrFormVO) {
        boolean flag = grainCommodityAttrService.updateAttr(baseAttrFormVO);
        return flag ? Result.success("更新成功", null)
                : Result.error("更新失败", null);
    }

    @PostMapping("/switchEnable")
    @PreAuthorize("@zhe.hasAuthority('sys:grain_commodity_attr:update')")
    public Result<Object> switchEnable(@RequestBody GrainCommodityAttr grainCommodityAttr) {
        boolean update = grainCommodityAttrService.updateById(grainCommodityAttr);
        return update ? Result.success("更新成功", null)
                : Result.error("更新失败", null);
    }

    /**
     * 根据categoryId查询销售属性
     * @param categoryId
     * @return
     */
    @GetMapping("/sale/list/{categoryId}/{brandId}")
    @PreAuthorize("@zhe.hasAuthority('sys:grain_commodity_attr:list')")
    public Result<List<GrainCommodityAttr>> selectSaleAttrByCategoryId(@PathVariable Long categoryId,
                                                                       @PathVariable Long brandId) {
        List<GrainCommodityAttr> grainCommodityAttrs =
                grainCommodityAttrService.selectSaleAttrByCategoryId(categoryId, brandId);
        return Result.success(grainCommodityAttrs);
    }

}
