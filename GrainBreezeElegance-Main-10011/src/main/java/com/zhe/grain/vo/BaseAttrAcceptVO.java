package com.zhe.grain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 接收前端的基本属性表格的数据
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAttrAcceptVO implements Serializable {
    @Serial
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
    /**
     * 属性类型[0-销售属性，1-基本属性]
     */
    private Byte attrType;

    private Long attrGroupId;

    private Long categoryId;
    /**
     * 品牌id
     */
    private Long brandId;
}
