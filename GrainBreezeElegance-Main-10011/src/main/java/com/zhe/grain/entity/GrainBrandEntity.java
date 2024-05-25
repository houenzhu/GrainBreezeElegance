package com.zhe.grain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhe.grain.exception.vaild.EnumValidate;
import com.zhe.grain.exception.vaild.SaveGroup;
import com.zhe.grain.exception.vaild.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

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
    @NotNull(message = "品牌id不能为空", groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id", groups = {SaveGroup.class})
    private Long id;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空", groups = {SaveGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 品牌logo
     */
    @NotBlank(message = "品牌logo不能为空", groups = {SaveGroup.class})
    @URL(message = "品牌logo必须是一个合法的url地址", groups = {UpdateGroup.class, SaveGroup.class})
    private String logo;
    /**
     * 品牌介绍
     */
    @NotBlank(message = "品牌介绍不能为空", groups = {SaveGroup.class})
    private String description;
    /**
     * 是否显示[0-不显示，1显示]
     */
    @NotNull(message = "是否显示不能为空", groups = {SaveGroup.class})
    @EnumValidate(values = {0, 1}, message = "是否显示只能为0或1",
            groups = {UpdateGroup.class, SaveGroup.class})
    private Integer isShow;
    /**
     * 首字母检索
     */
    @NotBlank(message = "首字母检索不能为空", groups = {SaveGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "首字母检索只能为a-z或A-Z",
            groups = {UpdateGroup.class, SaveGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = {SaveGroup.class})
    @Min(value = 0, message = "排序必须大于等于0", groups = {UpdateGroup.class, SaveGroup.class})
    private Integer sort;
}
