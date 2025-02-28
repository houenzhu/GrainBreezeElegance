package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 农业咨询与标签的关联表
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("agriculture_consultation_tags")
public class AgricultureConsultationTags implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 农业咨询ID
     */
    private Integer consultationId;

    /**
     * 标签ID
     */
    private Integer tagId;
}
