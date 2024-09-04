package com.zhe.grain.controller.user;

import com.zhe.grain.domain.user.SysRoleMenu;
import com.zhe.grain.service.user.SysRoleMenuService;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-28
 */
@RestController
@RequestMapping("/grain/sysRoleMenu")
public class SysRoleMenuController {
    private final SysRoleMenuService sysRoleMenuService;

    @Autowired
    public SysRoleMenuController(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @PostMapping("/save")
    @PreAuthorize("@zhe.hasAuthority('sys:role:menu:save')")
    public Result<Boolean> save(@RequestBody SysRoleMenu sysRoleMenu) {
        sysRoleMenuService.save(sysRoleMenu);
        return Result.success("添加角色权限成功, 系统将以半小时为一周期(从程序启动开始)" +
                "进行自动注销所有拥有该角色的用户," +
                " 进行重新登录！", true);
    }
}
