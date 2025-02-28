package com.zhe.grain.controller.user;

import com.zhe.grain.annotation.AdminLoginAnnotation;
import com.zhe.grain.domain.user.AdminEntity;
import com.zhe.grain.service.user.AdminService;
import com.zhe.grain.service.user.LoginUserService;
import com.zhe.grain.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



/**
 * @version 1.0
 * @Author 朱厚恩
 * 管理员的后端接口
 */

@RestController
@RequestMapping("/grain/admin")
@Slf4j
@Deprecated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private LoginUserService loginUserService;


    /**
     * 处理管理员登录接口
     * @return
     */
//    @PostMapping("/login")
//    public Result<Object> adminLogin(@RequestBody AdminLoginVO adminLoginVO) {
//        return loginUserService.login(adminLoginVO);
//    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public Result<Object> logout() {
        return loginUserService.logout();
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
    @PreAuthorize("hasAuthority('sys:dept:save')")
    public Result<Object> test1() {
        return Result.success("test1");
    }
}
