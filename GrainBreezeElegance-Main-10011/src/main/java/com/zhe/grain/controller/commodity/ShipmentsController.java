package com.zhe.grain.controller.commodity;

import com.zhe.grain.service.commodity.ShipmentsService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 物流信息表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-08-12
 */
@RestController
@RequestMapping("/grain/shipments")
@AllArgsConstructor
@Slf4j
public class ShipmentsController {
    private final ShipmentsService shipmentsService;

    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAuthority('sys:ship:list')")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        return Result.success(HttpStatus.OK.getReasonPhrase(), shipmentsService.page(params));
    }
}
