package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 农业咨询表
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("agriculture_consultations")
public class AgricultureConsultations implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 咨询的标题
     */
    private String title;

    /**
     * 咨询的分类
     */
    private String category;

    /**
     * 咨询的详细内容
     */
    private String content;

    /**
     * 咨询状态（0=未发布，1=已发布）
     */
    private Boolean status;

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

    /**
     * 发布者的用户ID
     */
    private Integer authorId;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 评论数
     */
    private Integer commentsCount;

    /**
     * 咨询的标签（可为空）
     */
    private String tags;

    /**
     * 点赞数
     */
    private Integer likesCount;

    /**
     * 封面
     */
    private String cover;

    /**
     * 摘要
     */
    private String abstractContent;
}
