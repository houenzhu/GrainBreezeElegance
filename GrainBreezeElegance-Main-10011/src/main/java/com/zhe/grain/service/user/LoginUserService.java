package com.zhe.grain.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.domain.LoginUser;
import com.zhe.grain.domain.SysUser;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.user.AdminLoginVO;
import jakarta.servlet.http.HttpServletRequest;


/**
 * @version 1.0
 * @Author 朱厚恩
 * 登录、注销接口
 */

public interface LoginUserService extends IService<LoginUser> {
    Result<Object> login(AdminLoginVO adminLoginVO, HttpServletRequest request);

    Result<Object> logout();

    // 获取个人信息
    Result<SysUser> getUserInfo();
}
