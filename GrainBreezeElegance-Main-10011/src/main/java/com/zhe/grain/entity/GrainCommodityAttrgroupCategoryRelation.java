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
 * 属性分组与分类关联表
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("grain_commodity_attrgroup_category_relation")
public class GrainCommodityAttrgroupCategoryRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性分组id
     */
    private Long attrGroupId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 属性分组名称
     */
    private String attrName;

    /**
     * 分类名称
     */
    private String categoryName;
}
