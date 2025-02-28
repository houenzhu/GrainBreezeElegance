package com.zhe.grain.domain.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 评论实体
 * 因为这个评论模块是大部分模块都必须具备的，所以需要有模块名来说明它来自哪个模块的哪个信息id
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@Document(collection = "comment")
public class Comment {
    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 用户ID
     */
    private String commentUserId;

    /**
     * 用户名
     */
    private String commentUsername;
    /**
     * 头像
     */
    private String commentAvatar;
    /**
     * 内容
     */
    private String commentContent;

    /**
     * 模块内某ID
     */
    private Long commentTagId;

    /**
     * 模块名
     */
    private String commentTagName;

    /**
     * 上级ID
     */
    private String commentSuperId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime commentCreateTime;
}
