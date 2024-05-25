package com.zhe.grain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 管理员实体
 */

@TableName("admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Deprecated
public class AdminEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 管理员id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 管理员名称
     */
    private String username;
    /**
     * 管理员密码(两次盐加密)
     */
    private String password;

    /**
     * 管理员手机号
     */
    private String phone;
    /**
     * 管理员盐值
     */
    private String slat;
    /**
     * 管理员头像
     */
    private String avatar;
    /**
     * 管理员年龄
     */
    private Integer age;
    /**
     * 管理员性别
     */
    private Integer gender;
    /**
     * 管理员创建时间
     */
    private Date createTime;
    /**
     * 管理员登录时间
     */
    private Date loginTime;

}
