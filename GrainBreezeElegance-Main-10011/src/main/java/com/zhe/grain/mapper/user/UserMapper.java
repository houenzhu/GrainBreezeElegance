package com.zhe.grain.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhe.grain.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface UserMapper extends BaseMapper<SysUser> {
    /**
     * 通过昵称查询用户id
     * @param nickName
     * @return
     */
    List<Long> findIdByNickname(@Param("nickName") String nickName);
}
