package com.zhe.grain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.entity.AdminEntity;
import com.zhe.grain.vo.AdminLoginVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface AdminService extends IService<AdminEntity> {
    String adminLogin(AdminLoginVO adminLoginVO, HttpServletRequest request,
                      HttpServletResponse response);


    AdminEntity getAdminByHeader(String header);
}
