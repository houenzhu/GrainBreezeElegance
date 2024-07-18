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
 * 商品spu信息介绍
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("commodity_spu_info_desc")
public class CommoditySpuInfoDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品 id
     */
    private Long spuId;

    /**
     * 商品介绍图片
     */
    private String decript;
}
