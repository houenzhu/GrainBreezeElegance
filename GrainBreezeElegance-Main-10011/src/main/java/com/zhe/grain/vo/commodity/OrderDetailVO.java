package com.zhe.grain.vo.commodity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetailVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String orderId;                  // 订单ID
    private String customerName;              // 顾客姓名
    private Byte orderStatus;                 // 订单状态
    private Byte paymentStatus;               // 支付状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;          // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedTime;          // 更新时间
    private List<ProductInfo> productList;   // 商品信息列表
    private BigDecimal totalAmount;           // 总金额
    private String shippingAddress;            // 收货地址
    private String billingAddress;             // 账单地址
    private Byte shippingMethod;               // 快递方式
    private String trackingNumber;             // 快递单号
    private String remarks;                    // 备注信息

    // Getters and Setters

    @Data
    public static class ProductInfo {
        private String productId;               // 商品ID
        private String productName;             // 商品名称
        private Integer quantity;                // 商品数量
        private BigDecimal unitPrice;           // 商品单价
        private BigDecimal totalPrice;          // 商品总价
    }

}
