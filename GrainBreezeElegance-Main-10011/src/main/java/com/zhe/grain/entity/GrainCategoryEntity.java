package com.zhe.grain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 商品分类实体
 */

@TableName("grain_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) // 链式调用
public class GrainCategoryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 层级
     */
    private Integer catLevel;
    /**
     * 是否显示 0 不显示 1 显示
     */
    @TableLogic
    private Integer isShow;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标
     */
    private String icon;
    /**
     * 统计单位
     */
    private String proUnit;
    /**
     * 商品数量
     */
    private Integer proCount;

    /**
     * 子分类
     */
    @TableField(exist = false)
    private List<GrainCategoryEntity> children;
}
