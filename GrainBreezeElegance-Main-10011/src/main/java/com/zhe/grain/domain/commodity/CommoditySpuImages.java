package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * spu 图片集
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("commodity_spu_images")
public class CommoditySpuImages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu_id
     */
    private Long spuId;

    /**
     * 图片名
     */
    private String imgName;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 顺序
     */
    private Integer imgSort;

    /**
     * 是否默认图
     */
    private Integer defaultImg;
}
