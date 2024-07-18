package com.zhe.grain.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhe.grain.entity.Menu;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
