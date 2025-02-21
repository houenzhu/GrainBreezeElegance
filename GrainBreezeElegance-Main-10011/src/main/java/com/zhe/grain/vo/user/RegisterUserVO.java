package com.zhe.grain.vo.user;

import com.zhe.grain.exception.vaild.SaveGroup;
import com.zhe.grain.exception.vaild.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "请输入用户名", groups = {SaveGroup.class, UpdateGroup.class})
    private String username;

    @NotBlank(message = "请选择性别", groups = {SaveGroup.class, UpdateGroup.class})
    private String sex;

    @NotBlank(message = "请输入手机号", groups = {SaveGroup.class, UpdateGroup.class})
    private String phone;

    @NotBlank(message = "请输入邮箱", groups = {SaveGroup.class, UpdateGroup.class})
    private String email;

    @NotBlank(message = "请选择用户类型", groups = {SaveGroup.class, UpdateGroup.class})
    private String userType;
}
