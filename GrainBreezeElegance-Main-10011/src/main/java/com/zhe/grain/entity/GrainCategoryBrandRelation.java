package com.zhe.grain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品牌分类关联表
 * </p>
 *
 * @author houen_zhu
 * @since 2024-05-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("grain_category_brand_relation")
public class GrainCategoryBrandRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 分类名称
     */
    private String categoryName;
}
