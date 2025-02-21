package com.zhe.grain.job;

import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.domain.Menu;
import com.zhe.grain.mapper.user.MenuMapper;
import com.zhe.grain.utils.RedisCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 定时任务
 */

@Component
@AllArgsConstructor
@Slf4j
public class MenuJob {

    private final RedisCache redisCache;
    private final MenuMapper menuMapper;


    // 定时检查管理员菜单权限是否更改？
    @Scheduled(cron = "0 */30 * * * ?")
    public void updateMenuScheduler() {
        // 管理员权限
        List<String> adminPermission = redisCache.getCacheList(RedisConstant.ADMIN_PERMISSION);
        // 用户权限
        List<String> userPermission = redisCache.getCacheList(RedisConstant.USER_PERMISSION);
        // 固定的超级管理员id
        List<Menu> adminMenu = menuMapper.selectPermsByUserIdOrderByDesc(1L);
        List<Menu> userMenu = menuMapper.selectPermsByUserIdOrderByDesc(4L);
        List<String> sysMenuItems = adminMenu.stream()
                .map(Menu::getPerms).toList();
        List<String> userMenuItems = userMenu.stream()
                .map(Menu::getPerms).toList();
        try {
            for (String menuItem : sysMenuItems) {
                if (!adminPermission.contains(menuItem)) {
                    redisCache.deleteObject(RedisConstant.ADMIN_PERMISSION);
                    Collection<String> keys = redisCache.keys(RedisConstant.USER_ENTITY_KEY + "*", 100);
                    // 强制退出所有的管理员, 进行重新登录
                    redisCache.deleteObject(keys);
                }
            }
            for (String menuItem : userMenuItems) {
                if (!userPermission.contains(menuItem)) {
                    redisCache.deleteObject(RedisConstant.USER_PERMISSION);
                    Collection<String> keys = redisCache.keys(RedisConstant.USER_ENTITY_KEY + "*", 100);
                    // 强制退出所有的用户, 进行重新登录
                    redisCache.deleteObject(keys);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
