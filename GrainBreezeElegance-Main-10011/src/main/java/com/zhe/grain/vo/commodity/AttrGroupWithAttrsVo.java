package com.zhe.grain.vo.commodity;

import com.zhe.grain.domain.commodity.GrainCommodityAttr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttrGroupWithAttrsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 组名
     */
    private String name;

    /**
     * 说明
     */
    private String description;

    private List<GrainCommodityAttr> grainCommodityAttrs;
}
