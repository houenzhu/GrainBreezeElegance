package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author houen_zhu
 * @since 2024-08-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private String orderId;

    /**
     * 顾客ID
     */
    private Integer customerId;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单状态
     *  0-待处理(订单已创建，但还未开始处理。),
     *  1-已确认(订单已确认，准备处理),
     *  2-已发货(订单已发货，正在运输中。),
     *  3-已送达(订单已送达给客户),
     *  4-已完成(订单处理已全部完成，无需进一步操作),
     *  5-已取消(订单已被取消，不再处理),
     *  6-已退货(客户已退货，订单进入退货处理流程)
     */
    private Byte orderStatus;

    /**
     * 支付状态
     * 如：0-待支付, 1-已支付, 3-支付失败, 4-已退款
     */
    private Byte paymentStatus;

    /**
     * 收货地址
     */
    private String shippingAddress;

    /**
     * 账单地址
     */
    private String billingAddress;

    /**
     * 快递单号
     */
    private String trackingNumber;

    /**
     * 快递方式
     */
    private Byte shippingMethod;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;
}
