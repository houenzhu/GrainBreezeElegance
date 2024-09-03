package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 销售属性和品牌关联表
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("grain_sale_attr_brand_relation")
public class GrainSaleAttrBrandRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 销售属性id
     */
    private Long saleAttrId;

    /**
     * 关联品牌id
     */
    private Long brandId;

    /**
     * 品牌名
     */
    private String brandName;
}
