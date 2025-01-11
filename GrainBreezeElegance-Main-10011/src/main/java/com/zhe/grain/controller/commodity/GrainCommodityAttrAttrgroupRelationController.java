package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.GrainCommodityAttr;
import com.zhe.grain.domain.commodity.GrainCommodityAttrAttrgroupRelation;
import com.zhe.grain.service.commodity.GrainCommodityAttrAttrgroupRelationService;
import com.zhe.grain.service.commodity.GrainCommodityAttrService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性和商品属性组的关联表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-16
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "grainCommodityAttrAttrgroupRelation")
public class GrainCommodityAttrAttrgroupRelationController {

    @Autowired
    private GrainCommodityAttrService grainCommodityAttrService;

    @Autowired
    private GrainCommodityAttrAttrgroupRelationService attrAttrgroupRelationService;

    @GetMapping("/{attrGroupId}/attr/relation")
    @PreAuthorize("@zhe.hasAuthority('sys:attrRe:find')")
    public Result<List<GrainCommodityAttr>> selectByAttrGroupId(@PathVariable("attrGroupId") Long attrGroupId) {
        List<GrainCommodityAttr> attrs = grainCommodityAttrService.selectByAttrGroupId(attrGroupId);
        return Result.success(attrs);
    }

    /**
     * 分页加载还没关联的商品属性分组
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    @PreAuthorize("@zhe.hasAuthority('sys:attrRe:noattr:relation')")
    public Result<PageUtils> selectNotInAttrIds(@PathVariable("attrGroupId") Long attrGroupId,
                                                 @RequestParam Map<String, Object> params) {
        PageUtils pageUtils = grainCommodityAttrService.selectNotInAttrIds(params, attrGroupId);
        return Result.success(pageUtils);
    }

    @PostMapping("/save")
    @PreAuthorize("@zhe.hasAuthority('sys:attrRe:save')")
    public Result<Boolean> save(@RequestBody List<GrainCommodityAttrAttrgroupRelation> relations) {
        boolean flag = attrAttrgroupRelationService.batchSaveAttrAttrGroupRelation(relations);
        return flag ? Result.success("添加关联成功!", true)
                : Result.error("添加关联失败!", false);
    }

    @PostMapping("/remove")
    @PreAuthorize("@zhe.hasAuthority('sys:attrRe:remove')")
    public Result<Integer> remove(@RequestBody List<GrainCommodityAttrAttrgroupRelation> relations) {
        int row = attrAttrgroupRelationService.removeRelation(relations);
        return row > 0 ? Result.success("成功删除了" + row + "条关联数据",  row)
                : Result.error("删除失败", 0);
    }

}
