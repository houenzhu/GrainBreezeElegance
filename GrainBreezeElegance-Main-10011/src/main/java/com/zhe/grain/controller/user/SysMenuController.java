package com.zhe.grain.controller.user;

import com.zhe.grain.service.user.SysMenuService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.user.SysMenuFormVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-25
 */
@RestController
@RequestMapping("/grain/sysMenu")
public class SysMenuController {
    private final SysMenuService sysMenuService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAuthority('sys:menu:list')")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysMenuService.queryPage(params);
        return Result.success(page);
    }

    @PostMapping("/savePermission")
    @PreAuthorize("@zhe.hasAuthority('sys:menu:savePermission')")
    public Result<Boolean> savePermission(@RequestBody SysMenuFormVO sysMenuFormVO) {
        sysMenuService.addPermission(sysMenuFormVO);
        return Result.success();
    }

    @GetMapping("/syncMenu")
    @PreAuthorize("@zhe.hasAuthority('sys:menu:syncMenu')")
    public Result syncMenu() {
        return sysMenuService.updateMenu() ? Result.success("更新成功!请刷新页面重新登录!", null) : Result.error();
    }
}
