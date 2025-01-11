package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.GrainCategoryBrandRelation;
import com.zhe.grain.domain.commodity.GrainCategoryEntity;
import com.zhe.grain.service.commodity.GrainCategoryBrainRelationService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌分类关联表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-11
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "grainCategoryBrandRelation")
public class GrainCategoryBrandRelationController {
    @Autowired
    private GrainCategoryBrainRelationService grainCategoryBrainRelationService;

    /**
     * 添加关联关系
     *
     * @param grainCategoryBrandRelation
     * @return
     */
    @PostMapping("/addRelateBrand")
    @PreAuthorize("hasAuthority('sys:brand:addRelateBrand')")
    public Result<Object> addRelateBrand(@RequestBody GrainCategoryBrandRelation grainCategoryBrandRelation) {
        return grainCategoryBrainRelationService.addRelation(grainCategoryBrandRelation);
    }

    /**
     * 根据品牌id查询所有关联关系
     *
     * @param brandId
     * @return
     */
    @GetMapping("/findRelateByBrandId/{brandId}")
    @PreAuthorize("hasAuthority('sys:brand:findRelateByBrandId')")
    public Result<List<GrainCategoryBrandRelation>> findRelateByBrandId(@PathVariable("brandId")
                                                                        Long brandId) {
        return grainCategoryBrainRelationService.showRelation(brandId);
    }

    /**
     * 获取没有关联过的分类信息
     *
     * @param brandId
     * @return
     */
    @GetMapping("/getNotSelectedCategory")
    @PreAuthorize("hasAuthority('sys:brand:getNotSelectedCategory')")
    public Result<List<GrainCategoryEntity>> getNotSelectedCategory(@RequestParam("brandId") Long brandId) {
        return grainCategoryBrainRelationService.getNotSelectedCategory(brandId);
    }

    /**
     * 删除关联关系
     *
     * @param map
     * @return
     */
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:brand:delete')")
    public Result<Object> delete(@RequestParam Map<String, Object> map) {
        WebUtil.changeStringToLong(map);
        Long id = (Long) map.get("id");
        return grainCategoryBrainRelationService.removeRelation(id);
    }

    @GetMapping("/brands/list/{categoryId}")
    @PreAuthorize("@zhe.hasAuthority('sys:brand:list:cateId')")
    public Result<List<GrainCategoryBrandRelation>> getBrandsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        List<GrainCategoryBrandRelation> relations =
                grainCategoryBrainRelationService.getRelationByCategoryId(categoryId);
        return Result.success(relations);
    }

}
