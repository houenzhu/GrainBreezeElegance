package com.zhe.grain.vo.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 雪花ID
     */
    private String orderId;

    private String customerName;

    private BigDecimal totalAmount;

    private Byte orderStatus;

    private Byte paymentStatus;

    private String shippingAddress;

    private String billingAddress;

    private Long skuId;

    private Integer quantity;

    private String trackingNumber;

    private Byte shippingMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;

}
