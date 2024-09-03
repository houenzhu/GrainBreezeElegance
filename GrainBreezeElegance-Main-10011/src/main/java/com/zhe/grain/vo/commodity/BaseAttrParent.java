package com.zhe.grain.vo.commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 基本属性的父类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAttrParent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long attrId;

    /**
     * 属性名
     */
    private String attrName;

    /**
     * 可选值列表[用逗号分隔]
     */
    private String valueSelect;

    /**
     * 启用状态[0 - 禁用，1 - 启用]
     */
    private Long enable;

}
