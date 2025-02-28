package com.zhe.grain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 接收前端的表单
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysMenuFormVO implements Serializable {

    /**
     * 权限名称
     */
    private String menuName;

    /**
     * 权限标识
     */
    private String path;

    /**
     * 具体权限
     */
    private String perms;

    /**
     * 权限备注
     */
    private String remark;
}
