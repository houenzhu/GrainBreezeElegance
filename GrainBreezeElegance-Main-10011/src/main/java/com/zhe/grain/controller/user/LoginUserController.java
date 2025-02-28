package com.zhe.grain.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import com.zhe.grain.domain.user.SysUser;
import com.zhe.grain.service.user.LoginUserService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.user.AdminLoginVO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


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
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(220, 80, 4);
        String imageBase64 = gifCaptcha.getImageBase64();
        String code = gifCaptcha.getCode();
        request.getSession().setAttribute("captcha", code);
        response.setContentType("image/png");
        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            byte[] decodeBytes = Base64.decode(imageBase64);
            os.write(decodeBytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            log.info("验证码异常: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                log.info("关闭输出流异常: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        log.info("session的验证码 = {}", request.getSession().getAttribute("captcha").toString());
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
