package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.AgricultureConsultationTags;
import com.zhe.grain.service.commodity.AgricultureConsultationTagsService;
import com.zhe.grain.service.commodity.AgricultureConsultationsService;
import com.zhe.grain.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 农业咨询与标签的关联表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-14
 */
@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "agricultureConsultationTags")
@AllArgsConstructor
public class AgricultureConsultationTagsController {

}
