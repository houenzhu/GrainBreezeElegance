package com.zhe.grain.controller.commodity;

import com.zhe.grain.annotation.CheckArgs;
import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.AgricultureConsultations;
import com.zhe.grain.record.AgricultureConsultationsRecord;
import com.zhe.grain.service.commodity.AgricultureConsultationsService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 农业咨询表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-11
 */
@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstant.API_PREFIX + "agricultureConsultations")
public class AgricultureConsultationsController {
    private final AgricultureConsultationsService agricultureConsultationsService;

    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAnyAuthority('sys:argi:info:page', 'user:argi:info:page')")
    @CheckArgs
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = agricultureConsultationsService.page(params);
        return Result.success(HttpStatus.OK.getReasonPhrase(), page);
    }

    @PostMapping("/pushConsultation")
    @PreAuthorize("@zhe.hasAnyAuthority('sys:argi:info:push', 'user:argi:info:push')")
    public Result<String> pushConsultation(@RequestBody AgricultureConsultationsRecord record) {
        agricultureConsultationsService.pushConsultation(record);
        return Result.success("发布成功!");
    }


}
