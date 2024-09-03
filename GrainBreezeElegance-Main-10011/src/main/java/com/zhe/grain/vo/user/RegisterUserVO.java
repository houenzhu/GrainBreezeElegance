package com.zhe.grain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 接收注册用户的VO对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterUserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String sex;
    private String phone;
    private String email;
    private String userType;
}
