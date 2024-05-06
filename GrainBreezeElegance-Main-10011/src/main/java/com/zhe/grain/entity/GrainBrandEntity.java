package com.zhe.grain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@TableName("grain_brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GrainBrandEntity {
    /**
     * 品牌id
     */
    @TableId
    private Integer id;
    /**
     * 品牌名
     */
    private String name;
    /**
     * 品牌logo
     */
    private String logo;
    /**
     * 品牌介绍
     */
    private String description;
    /**
     * 是否显示[0-不显示，1显示]
     */
    @TableLogic
    private Integer isShow;
    /**
     * 首字母检索
     */
    private String firstLetter;
    /**
     * 排序
     */
    private Integer sort;
}
