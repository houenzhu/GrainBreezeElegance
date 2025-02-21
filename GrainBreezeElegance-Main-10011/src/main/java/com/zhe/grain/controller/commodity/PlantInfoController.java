package com.zhe.grain.controller.commodity;

import com.zhe.grain.annotation.CheckArgs;
import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.PlantInfo;
import com.zhe.grain.dto.PlantInfoDTO;
import com.zhe.grain.service.commodity.PlantInfoService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.ResultMsgEnum;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2025-02-19
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "plantInfo")
@AllArgsConstructor
public class PlantInfoController {
    private final PlantInfoService plantInfoService;

    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAnyAuthority('sys:plant:list', 'user:plant:list')")
    @CheckArgs
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        return Result.success(plantInfoService.page(params));
    }

    @PostMapping("/save")
    @PreAuthorize("@zhe.hasAuthority('sys:plant:save')")
    @CheckArgs
    public Result<String> save(@RequestBody PlantInfoDTO plantInfoDTO) {
        try {
            plantInfoService.savePlantInfo(plantInfoDTO);
            return Result.success("添加成功!");
        } catch (Exception e) {
            return Result.error(ResultMsgEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("@zhe.hasAnyAuthority('sys:plant:findOne', 'user:plant:findOne')")
    public Result<PlantInfoDTO> findById(@PathVariable("id") Long id) {
        return Result.success(plantInfoService.findById(id));
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("@zhe.hasAuthority('sys:plant:delete')")
    public Result<Object> deleteById(@PathVariable("id") Long id) {
        plantInfoService.deleteById(id);
        return Result.success();
    }
}
