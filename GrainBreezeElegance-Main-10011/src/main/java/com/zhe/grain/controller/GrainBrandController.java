package com.zhe.grain.controller;

import com.zhe.grain.annotation.AdminLoginAnnotation;
import com.zhe.grain.entity.GrainBrandEntity;
import com.zhe.grain.service.GrainBrandService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@RestController
@RequestMapping("/grain/brand")
public class GrainBrandController {

    @Autowired
    private GrainBrandService grainBrandService;

    @GetMapping("/list")
    @AdminLoginAnnotation
    public Result<Object> list(@RequestParam Map<String, Object> params) {
        PageUtils page = grainBrandService.queryPage(params);
        System.out.println("page = " + page);
        return Result.success(page);
    }

    /**
     * 通过id找唯一品牌
     * @param id id
     * @return
     */
    @GetMapping("/info/{id}")
    @AdminLoginAnnotation
    public Result<GrainBrandEntity> info(@PathVariable("id") Long id) {
        return Result.success(grainBrandService.getById(id));
    }

    /**
     * 保存
     * @param grainBrandEntity
     * @return
     */
    @PostMapping("/save")
    @AdminLoginAnnotation
    public Result<Object> save(@RequestBody GrainBrandEntity grainBrandEntity) {
        if (grainBrandService.save(grainBrandEntity)) {
            return Result.success(null);
        } else {
            return Result.error();
        }
    }

    @PostMapping("/remove")
    @AdminLoginAnnotation
    public Result<Object> remove(@RequestBody List<Long> ids) {
        if (grainBrandService.removeBatchByIds(ids)) {
            return Result.success(null);
        } else {
            return Result.error();
        }
    }
}
