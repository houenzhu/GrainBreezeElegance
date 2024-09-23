package com.zhe.grain.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.domain.user.AdminEntity;
import com.zhe.grain.vo.user.AdminLoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Deprecated
public interface AdminService extends IService<AdminEntity> {
    String adminLogin(AdminLoginVO adminLoginVO, HttpServletRequest request,
                      HttpServletResponse response);


    AdminEntity getAdminByHeader(String header);

    // 注销登录接口
    boolean logout(String token);


}
