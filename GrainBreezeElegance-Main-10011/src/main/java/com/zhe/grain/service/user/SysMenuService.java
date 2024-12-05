package com.zhe.grain.service.user;

import com.zhe.grain.domain.user.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.vo.user.SysMenuFormVO;

import java.util.Map;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-25
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 分页查询权限菜单
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 新增权限菜单
     * @param sysMenuFormVO
     */
    void addPermission(SysMenuFormVO sysMenuFormVO);

    /**
     * 手动更新权限
     * @return
     */
    boolean updateMenu();
}
