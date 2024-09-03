package com.zhe.grain.job;

import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.domain.Menu;
import com.zhe.grain.mapper.user.MenuMapper;
import com.zhe.grain.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 定时任务
 */

@Component
@Slf4j
public class MenuJob {

    private final RedisCache redisCache;
    private final MenuMapper menuMapper;

    @Autowired
    public MenuJob(RedisCache redisCache,
                   MenuMapper menuMapper) {
        this.redisCache = redisCache;
        this.menuMapper = menuMapper;
    }

    // 定时检查管理员菜单权限是否更改？
    @Scheduled(cron = "0 */30 * * * ?")
    public void job1() {
        log.info("权限对比开始进行...");
        long startTime = System.currentTimeMillis();
        List<String> cacheList = redisCache.getCacheList(RedisConstant.ADMIN_PERMISSION);
        // 固定的超级管理员id
        List<Menu> menus = menuMapper.selectPermsByUserIdOrderByDesc(1L);
        List<String> menuItems = menus.stream()
                .map(Menu::getPerms).toList();
        for (String menuItem : menuItems) {
            if (!cacheList.contains(menuItem)) {
                log.info("检测到有新的权限加入, 正在进行重新加载......");
                redisCache.deleteObject(RedisConstant.ADMIN_PERMISSION);
                Collection<String> keys = redisCache.keys("admin:user:*", 100);
                // 强制退出所有的管理员, 进行重新登录
                redisCache.deleteObject(keys);
                long endTime = System.currentTimeMillis();
                log.info("检测结束, 更改完成, 用时{}ms", endTime - startTime);
                return;
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("检测结束, 无更改, 用时{}ms", endTime - startTime);
    }
}
