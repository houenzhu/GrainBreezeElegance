package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.Tags;
import com.zhe.grain.service.commodity.TagsService;
import com.zhe.grain.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-14
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "tags")
@AllArgsConstructor
public class TagsController {
    private TagsService tagsService;

    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAnyAuthority('sys:tags:list', 'user:tags:list')")
    public Result<List<Tags>> list(@RequestParam("type") String type) {
        return Result.success(tagsService.list(type));
    }
}
