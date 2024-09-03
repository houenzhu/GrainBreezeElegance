package com.zhe.grain.service.user;

import com.zhe.grain.domain.user.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-28
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 保存角色菜单关联
     * @param sysRoleMenu
     */
    void saveRoleMenu(SysRoleMenu sysRoleMenu);
}
