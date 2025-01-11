package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.GrainAttrCategoryRelation;
import com.zhe.grain.domain.commodity.GrainCategoryEntity;
import com.zhe.grain.service.commodity.GrainAttrCategoryRelationService;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性和商品分类的关联表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-19
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "grainAttrCategoryRelation")
public class GrainAttrCategoryRelationController {

    @Autowired
    private GrainAttrCategoryRelationService grainAttrCategoryRelationService;

    /**
     * 删除关联
     * @param params
     * @return
     */
    @PostMapping("/removeRelation")
    @PreAuthorize("@zhe.hasAuthority('sys:cateRe:remove')")
    public Result<Boolean> removeRelation(@RequestParam Map<String, Object> params) {
        boolean flag = grainAttrCategoryRelationService.removeRelation(params);
        return flag ? Result.success("删除成功!", true)
                : Result.error("删除失败", false);
    }

    /**
     * 获取未关联的分类
     * @param attrId
     * @return
     */
    @GetMapping("/getNotSelectedCategory")
    @PreAuthorize("@zhe.hasAuthority('sys:cateRe:getNotSelect')")
    public Result<List<GrainCategoryEntity>> getNotSelectedCategory(@RequestParam("attrId") Long attrId) {
        List<GrainCategoryEntity> list = grainAttrCategoryRelationService.getNotSelectedCategory(attrId);
        return Result.success(list);
    }

    /**
     * 添加关联
     * @param grainAttrCategoryRelation
     * @return
     */
    @Deprecated
    @PostMapping("/addRelation")
    public Result<Boolean> addRelation(@RequestBody GrainAttrCategoryRelation grainAttrCategoryRelation) {
        boolean flag = grainAttrCategoryRelationService.addRelation(grainAttrCategoryRelation);
        return flag ? Result.success("添加成功!", true)
                : Result.error("添加失败, 请重新选择", false);
    }


}
