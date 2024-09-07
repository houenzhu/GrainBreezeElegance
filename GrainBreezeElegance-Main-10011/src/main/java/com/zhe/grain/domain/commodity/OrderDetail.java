package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author houen_zhu
 * @since 2024-09-05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 购买的产品id
     */
    private Long skuId;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 备用字段1
     */
    private String bak1;

    /**
     * 备用字段2
     */
    private String bak2;

    /**
     * 备用字段3
     */
    private String bak3;

    /**
     * 备用字段4
     */
    private String bak4;

    /**
     * 备用字段5
     */
    private String bak5;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除, 0未删除, 1已删除
     */
    private Byte isDeleted;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 更新者
     */
    private Integer updateBy;
}
