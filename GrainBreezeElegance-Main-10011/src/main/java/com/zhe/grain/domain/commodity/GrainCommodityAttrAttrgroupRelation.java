package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品属性和商品属性组的关联表
 * </p>
 *
 * @author houen_zhu
 * @since 2024-06-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("grain_commodity_attr_attrgroup_relation")
public class GrainCommodityAttrAttrgroupRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性id
     */
    private Long attrId;

    /**
     * 属性分组id
     */
    private Long attrGroupId;
}
