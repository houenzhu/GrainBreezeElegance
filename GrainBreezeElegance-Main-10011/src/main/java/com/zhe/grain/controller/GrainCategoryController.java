package com.zhe.grain.controller;

import com.zhe.grain.entity.GrainCategoryEntity;
import com.zhe.grain.service.GrainCategoryService;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 商品分类控制器
 */

@RestController
@RequestMapping("/grain/category")
public class GrainCategoryController {

    @Autowired
    private GrainCategoryService grainCategoryService;

    @GetMapping("/treeList")
    public Result<List<GrainCategoryEntity>> getCategoryTreeList() {
        List<GrainCategoryEntity> categoryList = grainCategoryService.getCategoryList();
        return Result.success(categoryList);
    }

    @PostMapping("/addTopNode")
    public Result<Object> addTopNode(@RequestBody GrainCategoryEntity grainCategoryEntity) {
        grainCategoryService.addTopNode(grainCategoryEntity);
        return Result.success();
    }

    /**
     * 批量删除结点
     * @param ids
     * @return
     */
    @DeleteMapping("/delete")
    public Result<Object> deleteNodes(@RequestBody Long[] ids) {
        grainCategoryService.removeBatchByIds(Arrays.asList(ids));
        return Result.success();
    }
}
