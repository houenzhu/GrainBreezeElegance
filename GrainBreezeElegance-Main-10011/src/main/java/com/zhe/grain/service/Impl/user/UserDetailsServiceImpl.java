package com.zhe.grain.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.domain.LoginUser;
import com.zhe.grain.domain.SysUser;
import com.zhe.grain.mapper.user.MenuMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.utils.RedisCache;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserMapper userMapper;


    private final MenuMapper menuMapper;


    private final RedisCache redisCache;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        SysUser sysUser = userMapper.selectOne(
                new QueryWrapper<SysUser>().eq("username", username)
        );
        List<String> adminPermissioList = redisCache.getCacheList(RedisConstant.ADMIN_PERMISSION);
        List<String> userPermissionList = redisCache.getCacheList(RedisConstant.USER_PERMISSION);
        Long userId = sysUser.getId();
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        String userType = sysUser.getUserType();
        if ("0".equals(userType)) {
            if (CollectionUtils.isEmpty(adminPermissioList)) {
                redisCache.setCacheList(RedisConstant.ADMIN_PERMISSION, perms);
                return new LoginUser(sysUser, perms);
            }
            return new LoginUser(sysUser, adminPermissioList);
        } else {
            if (CollectionUtils.isEmpty(userPermissionList)) {
                redisCache.setCacheList(RedisConstant.USER_PERMISSION, perms);
                return new LoginUser(sysUser, perms);
            }
            return new LoginUser(sysUser, userPermissionList);
        }

    }
}
