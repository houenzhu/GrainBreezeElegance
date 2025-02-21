package com.zhe.grain.mapper.commodity;

import com.zhe.grain.domain.commodity.AgricultureConsultations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 农业咨询表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-11
 */
@Mapper
public interface AgricultureConsultationsMapper extends BaseMapper<AgricultureConsultations> {
    List<AgricultureConsultations> selectAllByTags(@Param("tag") Integer tag,
                                                   @Param("pageNum") Integer pageNum,
                                                   @Param("pageSize") Integer pageSize);

    Integer getTotalCountByTags(@Param("tag") Integer tag);

    void insertRelationConTag(@Param("relationId") Integer relationId, @Param("tagId") Integer tagId);
}
