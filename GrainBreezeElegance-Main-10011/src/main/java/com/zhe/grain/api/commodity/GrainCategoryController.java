package com.zhe.grain.api.commodity;

import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.domain.commodity.GrainCategoryEntity;
import com.zhe.grain.service.commodity.GrainCategoryService;
import com.zhe.grain.utils.RedisCache;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取树形分类
     * @return
     */
    @GetMapping("/treeList")
    @PreAuthorize("hasAuthority('sys:category:list')")
    public Result<List<GrainCategoryEntity>> getCategoryTreeList() {
        List<GrainCategoryEntity> categoryList = grainCategoryService.getCategoryList();
        return Result.success(categoryList);
    }

    /**
     * 添加结点
     *
     * @param grainCategoryEntity
     * @return
     */
    @PostMapping("/addNode")
    @PreAuthorize("hasAuthority('sys:category:addNode')")
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
    @PreAuthorize("hasAuthority('sys:category:delete')")
    public Result<Object> deleteNodes(@RequestBody List<Long> ids) {
        grainCategoryService.batchDeleteNodes(ids);
        return Result.success();
    }

    /**
     * 通过id查找商品分类
     *
     * @param id
     * @return
     */
    @GetMapping("/getCategoryById")
    @PreAuthorize("hasAuthority('sys:category:getCategoryById')")
    public Result<GrainCategoryEntity> getCategoryById(@RequestParam("id") Long id) {
        return Result.success(grainCategoryService.getById(id));
    }

    /**
     * 更新结点信息
     *
     * @param grainCategoryEntity
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:category:update')")
    public Result<Object> update(@RequestBody GrainCategoryEntity grainCategoryEntity) {
        grainCategoryService.updateById(grainCategoryEntity);
        redisCache.deleteObject(RedisConstant.CATEGORY_LIST_TREE);
        return Result.success();
    }
}
