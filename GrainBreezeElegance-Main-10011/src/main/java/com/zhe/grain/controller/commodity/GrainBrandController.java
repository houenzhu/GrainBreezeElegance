package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.GrainBrandEntity;
import com.zhe.grain.exception.vaild.SaveGroup;
import com.zhe.grain.service.commodity.GrainBrandService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "brand")
@Slf4j
public class GrainBrandController {

    @Autowired
    private GrainBrandService grainBrandService;

    /**
     * 品牌分页查询
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:brand:list')")
    public Result<Object> list(@RequestParam Map<String, Object> params) {
        System.out.println("params = " + params);
        PageUtils page = grainBrandService.queryPage(params);
//        System.out.println("page = " + page);
        return Result.success(page);
    }

    /**
     * 通过id找唯一品牌
     *
     * @param id id
     * @return
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:brand:info')")
    public Result<GrainBrandEntity> info(@PathVariable("id") Long id) {
        return Result.success(grainBrandService.getById(id));
    }

    /**
     * 保存
     *
     * @param grainBrandEntity
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:brand:save')")
    public Result<Object> save(@Validated({SaveGroup.class})
                                   @RequestBody GrainBrandEntity grainBrandEntity) {
        if (grainBrandService.save(grainBrandEntity)) {
            return Result.success(null);
        } else {
            return Result.error();
        }
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('sys:brand:remove')")
    public Result<Object> remove(@RequestBody List<Long> ids) {
        grainBrandService.batchDeleteBrandIds(ids);
        return Result.success(null);
    }

    /**
     * 修改
     *
     * @param grainBrandEntity
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:brand:update')")
    public Result<Object> update(@RequestBody GrainBrandEntity grainBrandEntity) {
        grainBrandService.update(grainBrandEntity);
        return Result.success();
    }
}
