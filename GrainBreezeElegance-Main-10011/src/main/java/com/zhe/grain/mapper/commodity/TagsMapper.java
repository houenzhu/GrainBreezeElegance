package com.zhe.grain.mapper.commodity;

import com.zhe.grain.domain.commodity.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-14
 */
@Mapper
public interface TagsMapper extends BaseMapper<Tags> {
    List<Tags> list();
    Tags selectById(@Param("id") Integer id);
}
