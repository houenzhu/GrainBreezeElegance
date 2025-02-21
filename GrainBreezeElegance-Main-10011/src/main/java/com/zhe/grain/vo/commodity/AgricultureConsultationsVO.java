package com.zhe.grain.vo.commodity;

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
 * 农业咨询VO表
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AgricultureConsultationsVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 发布者的用户名字
     */
    private String authorName;

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
     * 咨询类型（1=科普，2=问答贴，3=技术性指导）
     */
    private Integer type;

    /**
     * 封面图
     */
    private String cover;
}
