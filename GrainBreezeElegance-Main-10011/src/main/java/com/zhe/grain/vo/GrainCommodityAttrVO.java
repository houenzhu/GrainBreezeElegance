package com.zhe.grain.vo;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 返回前端数据格式
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrainCommodityAttrVO extends BaseAttrParent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性类型
     */
    private Byte attrType;

    /**
     * 所属分类
     */
    private String categoryName;

    private List<Long> categoryIds;
    private Long attrGroupId;
    private Long brandId;
}
