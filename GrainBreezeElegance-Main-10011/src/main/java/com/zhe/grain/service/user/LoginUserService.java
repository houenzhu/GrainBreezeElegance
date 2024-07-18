package com.zhe.grain.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.entity.LoginUser;
import com.zhe.grain.entity.SysUser;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.AdminLoginVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface LoginUserService extends IService<LoginUser> {
    Result<Object> login(AdminLoginVO adminLoginVO, HttpServletRequest request);

    Result<Object> logout();

    // 获取个人信息
    Result<SysUser> getUserInfo();
}
