package com.zhe.grain.vo.commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 发送前端基本属性格式
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseAttrFormVO extends BaseAttrParent implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 属性类型[0-销售属性，1-基本属性]
     */
    private Byte attrType;

    /**
     * 多个分类id
     */
    private Long[] categoryIds;

    /**
     * 属性分组id(基本属性)
     */
    private Long attrGroupId;

    /**
     * 所属品牌id(销售属性)
     */
    private Long brandId;
}
