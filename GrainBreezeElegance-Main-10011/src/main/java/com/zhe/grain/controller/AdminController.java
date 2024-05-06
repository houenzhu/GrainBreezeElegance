package com.zhe.grain.controller;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.common.Fonts;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import com.zhe.grain.annotation.AdminLoginAnnotation;
import com.zhe.grain.entity.AdminEntity;
import com.zhe.grain.exception.LoginException;
import com.zhe.grain.service.AdminService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.ResultMsgEnum;
import com.zhe.grain.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 管理员的后端接口
 */

@RestController
@RequestMapping("/grain/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 处理管理员登录接口
     * @return
     */
    @PostMapping("/login")
    public Result<String> adminLogin(@RequestBody AdminLoginVO adminLoginVO,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        String userTicket = adminService.adminLogin(adminLoginVO, request, response);
        if (!StringUtils.hasText(userTicket)) {
            throw new LoginException();
        }
        return Result.success(userTicket);
    }

    /**
     * 生成验证码
     * @param request
     * @param response
     */
    @GetMapping("/captcha")
    public void HappyCaptcha(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response)
                .style(CaptchaStyle.ANIM)
                .type(CaptchaType.NUMBER)
                .length(6)
                .width(220)
                .height(80)
                .font(Fonts.getInstance().zhFont())
                .build().finish();
        log.info("session的验证码 = {}", request.getSession().getAttribute("happy-captcha").toString());
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    @AdminLoginAnnotation
    public Result<Object> logout(@RequestParam String adminToken) {
        if (adminService.logout(adminToken)) {
            return Result.success(ResultMsgEnum.LOGOUT_SUCCESS, null);
        } else {
            return Result.error(ResultMsgEnum.LOGOUT_ERROR, null);
        }
    }

    /**
     * 获取管理员个人信息
     * @return
     */
    @GetMapping("/getAdminInfo")
    @AdminLoginAnnotation
    public Result<AdminEntity> getAdminInfo(@RequestParam String adminToken) {
        AdminEntity admin = adminService.getAdminByHeader(adminToken);
        if (null == admin) {
            return Result.error();
        } else {
            return Result.success(admin);
        }
    }


    @GetMapping("/t1")
    @AdminLoginAnnotation
    public Result<Object> test1() {
        return Result.success("test1");
    }
}
