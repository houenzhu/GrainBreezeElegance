package com.zhe.grain.controller;

import com.zhe.grain.entity.AdminEntity;
import com.zhe.grain.exception.LoginException;
import com.zhe.grain.service.AdminService;
import com.zhe.grain.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 管理员的后端接口
 */

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;


    /**
     * 处理管理员登录接口
     * @return
     */
    @PostMapping("/login")
    public Result<String> adminLogin(@RequestBody AdminEntity adminEntity,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        String userTicket = adminService.adminLogin(adminEntity, request, response);
        if (!StringUtils.hasText(userTicket)) {
            throw new LoginException();
        }
        return Result.success(userTicket);
    }

}
