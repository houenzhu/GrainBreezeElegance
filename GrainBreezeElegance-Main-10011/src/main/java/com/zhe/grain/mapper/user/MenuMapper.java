package com.zhe.grain.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhe.grain.domain.user.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(@Param("userId") Long userId);
    // TODO: 2024/7/27 需要一个排序的一个查询权限的接口(根据id的降序)
    List<Menu> selectPermsByUserIdOrderByDesc(@Param("userId") Long userId);
}
