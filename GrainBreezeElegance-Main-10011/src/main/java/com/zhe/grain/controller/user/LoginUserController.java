package com.zhe.grain.controller.user;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.common.Fonts;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import com.zhe.grain.domain.SysUser;
import com.zhe.grain.service.user.LoginUserService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.user.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 新的登录|注销控制器
 */

@RestController
@RequestMapping("/grain/user")
@Slf4j
public class LoginUserController {

    @Autowired
    private LoginUserService loginUserService;


    /**
     * 处理管理员登录接口
     * @return
     */
    @PostMapping("/login")
    public Result<Object> adminLogin(@RequestBody AdminLoginVO adminLoginVO,
                                     HttpServletRequest request) {
        return loginUserService.login(adminLoginVO, request);
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

    @GetMapping("/info")
    @PreAuthorize("hasAnyAuthority('sys:user:info')")
    public Result<SysUser> getUserInfo() {
        return loginUserService.getUserInfo();
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Result<Object> logout() {
        return loginUserService.logout();
    }


}
