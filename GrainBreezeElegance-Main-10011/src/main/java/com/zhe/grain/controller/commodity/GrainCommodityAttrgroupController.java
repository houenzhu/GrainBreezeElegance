package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.GrainCommodityAttrgroup;
import com.zhe.grain.domain.commodity.GrainCommodityAttrgroupCategoryRelation;
import com.zhe.grain.service.commodity.GrainCommodityAttrgroupService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.commodity.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性分组 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "commodityAttrgroup")
public class GrainCommodityAttrgroupController {

    @Autowired
    private GrainCommodityAttrgroupService grainCommodityAttrgroupService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:attr_group:list')")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        return grainCommodityAttrgroupService.listByPage(params);
    }

    /**
     * 新增
     * @param grainCommodityAttrgroup
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:attr_group:save')")
    public Result<Object> save(@RequestBody GrainCommodityAttrgroup grainCommodityAttrgroup) {
        boolean save = grainCommodityAttrgroupService.save(grainCommodityAttrgroup);
        return save ? Result.success() : Result.error();
    }

    /**
     * 修改
     * @param grainCommodityAttrgroup
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:attr_group:update')")
    public Result<Object> update(@RequestBody GrainCommodityAttrgroup grainCommodityAttrgroup) {
        boolean update = grainCommodityAttrgroupService.updateById(grainCommodityAttrgroup);
        return update ? Result.success() : Result.error();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    @PreAuthorize("hasAuthority('sys:attr_group:findById')")
    public Result<GrainCommodityAttrgroup> findById(@PathVariable("id") Long id) {
        GrainCommodityAttrgroup grainCommodityAttrgroup = grainCommodityAttrgroupService.getById(id);
        return grainCommodityAttrgroup != null
                ? Result.success(grainCommodityAttrgroup)
                : Result.error();
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('sys:attr_group:remove')")
    public Result<Object> remove(@RequestBody Long[] attrGroupIds) {
        return grainCommodityAttrgroupService.batchDelete(attrGroupIds);
    }

    /**
     * 获取所有属性分组下拉列表
     * @return
     */
    @GetMapping("/selectList")
    @PreAuthorize("hasAnyAuthority('sys:attr_group:selectList')")
    public Result<List<GrainCommodityAttrgroup>> selectList() {
        List<GrainCommodityAttrgroup> list = grainCommodityAttrgroupService.list();
        return Result.success(list);
    }


    /**
     * 获取某个分类的所有的分组下拉列表
     * @return
     */
    @GetMapping("/selectOne/{categoryId}")
    @PreAuthorize("@zhe.hasAuthority('sys:attr_group:selectOne')")
    public Result<List<GrainCommodityAttrgroupCategoryRelation>> selectOne(@PathVariable("categoryId") Long categoryId) {
        List<GrainCommodityAttrgroupCategoryRelation> list =
                grainCommodityAttrgroupService.selectOne(categoryId);
        return Result.success(list);
    }

    @GetMapping("/{categoryId}/withattr")
    @PreAuthorize("@zhe.hasAuthority('sys:attr_group:withattr')")
    public Result<List<AttrGroupWithAttrsVo>> getAttrGroupWithAttrsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<AttrGroupWithAttrsVo> attrGroupWithAttrsByCategoryId =
                grainCommodityAttrgroupService.getAttrGroupWithAttrsByCategoryId(categoryId);
        return Result.success(attrGroupWithAttrsByCategoryId);
    }
}
