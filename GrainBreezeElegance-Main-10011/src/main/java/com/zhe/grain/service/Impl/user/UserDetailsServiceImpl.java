package com.zhe.grain.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.entity.LoginUser;
import com.zhe.grain.entity.SysUser;
import com.zhe.grain.mapper.user.MenuMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.utils.RedisCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisCache redisCache;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        SysUser sysUser = userMapper.selectOne(
                new QueryWrapper<SysUser>().eq("username", username)
        );
        List<String> permissionList = redisCache.getCacheList(RedisConstant.ADMIN_PERMISSION);
        if (CollectionUtils.isEmpty(permissionList)) {
            permissionList = menuMapper.selectPermsByUserId(sysUser.getId());
            redisCache.setCacheList(RedisConstant.ADMIN_PERMISSION, permissionList);
        }
        return new LoginUser(sysUser, permissionList);
    }
}
