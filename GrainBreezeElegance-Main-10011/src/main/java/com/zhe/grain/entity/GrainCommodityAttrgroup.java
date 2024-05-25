package com.zhe.grain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品属性分组
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("grain_commodity_attrgroup")
public class GrainCommodityAttrgroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 组名
     */
    private String name;

    /**
     * 说明
     */
    private String description;

    /**
     * 组图标
     */
    private String icon;

    /**
     * 所属分类id
     */
    private Long categoryId;
}
