package com.zhe.grain.vo.commodity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttrGroupRelationVO implements Serializable {
    private Long attrGroupId;
    private Long categoryId;
}
