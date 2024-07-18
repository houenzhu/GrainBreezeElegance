package com.zhe.grain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author 韩顺平
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;
}
