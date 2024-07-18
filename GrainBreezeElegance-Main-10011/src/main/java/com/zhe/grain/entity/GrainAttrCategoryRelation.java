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
 * 商品属性和商品分类的关联表
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("grain_attr_category_relation")
public class GrainAttrCategoryRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 基本属性id
     */
    private Long attrId;
}
