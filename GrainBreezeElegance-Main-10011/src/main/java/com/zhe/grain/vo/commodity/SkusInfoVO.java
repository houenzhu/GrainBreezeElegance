package com.zhe.grain.vo.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 顾客购买商品信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SkusInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * skuId
     */
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;

    /**
     * spuId
     */
    private Long spuId;

    /**
     * sku 名称
     */
    private String skuName;

    /**
     * sku 介绍描述
     */
    private String skuDesc;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 默认图片
     */
    private String skuDefaultImg;

    /**
     * 标题
     */
    private String skuTitle;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;
}
