package com.zhe.grain.domain.user;

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
 */

@TableName("weixin_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WeixinUserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 微信用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 微信用户名
     */
    private String userName;
    /**
     * 电话号码
     */
    private String telNumber;
    /**
     * 微信头像
     */
    private String avatarUrl;
    /**
     * 省份
     */
    private String provinceName;
    /**
     * 市
     */
    private String cityName;
    /**
     * 区
     */
    private String countryName;
    /**
     * 详细地址
     */
    private String detailInfo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 登录时间
     */
    private Date loginTime;
}
