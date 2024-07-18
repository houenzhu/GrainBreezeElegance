package com.zhe.grain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品spu信息
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("commodity_spu_info")
public class CommoditySpuInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商品 id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品描述
     */
    private String spuDescription;

    /**
     * 所属分类id
     */
    private Long catalogId;

    /**
     * 品牌 id
     */
    private Long brandId;

    /**
     * 商品重量
     */
    private BigDecimal weight;

    /**
     * 上架状态[0- 下架，1- 上架]
     */
    private Integer publishStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
