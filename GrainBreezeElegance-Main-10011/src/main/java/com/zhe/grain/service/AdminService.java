package com.zhe.grain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.entity.AdminEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface AdminService extends IService<AdminEntity> {
    String adminLogin(AdminEntity adminEntity, HttpServletRequest request,
                      HttpServletResponse response);
}
