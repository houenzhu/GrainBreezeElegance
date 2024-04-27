package com.zhe.grain.vo;

import com.zhe.grain.entity.AdminEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginVO {

    /**
     * 管理员手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;
}
