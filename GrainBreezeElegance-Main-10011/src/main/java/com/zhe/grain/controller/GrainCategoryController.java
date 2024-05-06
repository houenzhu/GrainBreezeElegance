package com.zhe.grain.controller;

import com.zhe.grain.annotation.AdminLoginAnnotation;
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
    @AdminLoginAnnotation
    public Result<List<GrainCategoryEntity>> getCategoryTreeList() {
        List<GrainCategoryEntity> categoryList = grainCategoryService.getCategoryList();
        return Result.success(categoryList);
    }

    /**
     * 添加结点
     * @param grainCategoryEntity
     * @return
     */
    @PostMapping("/addNode")
    @AdminLoginAnnotation
    public Result<Object> addTopNode(@RequestBody GrainCategoryEntity grainCategoryEntity) {
        grainCategoryService.addNode(grainCategoryEntity);
        return Result.success();
    }

    /**
     * 批量删除结点
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @AdminLoginAnnotation
    public Result<Object> deleteNodes(@RequestBody Long[] ids) {
        grainCategoryService.removeBatchByIds(Arrays.asList(ids));
        return Result.success();
    }

    /**
     * 通过id查找商品分类
     * @param id
     * @return
     */
    @GetMapping("/getCategoryById")
    @AdminLoginAnnotation
    public Result<GrainCategoryEntity> getCategoryById(@RequestParam("id") Long id) {
        return Result.success(grainCategoryService.getById(id));
    }

    /**
     * 更新结点信息
     * @param grainCategoryEntity
     * @return
     */
    @PostMapping("/update")
    @AdminLoginAnnotation
    public Result<Object> update(@RequestBody GrainCategoryEntity grainCategoryEntity) {
        grainCategoryService.updateById(grainCategoryEntity);
        return Result.success();
    }
}
