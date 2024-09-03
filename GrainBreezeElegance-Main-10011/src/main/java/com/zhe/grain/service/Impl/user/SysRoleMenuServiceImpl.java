package com.zhe.grain.service.Impl.user;

import com.zhe.grain.domain.user.SysRoleMenu;
import com.zhe.grain.mapper.user.SysRoleMenuMapper;
import com.zhe.grain.service.user.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-28
 */
@Service
public class SysRoleMenuServiceImpl
        extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
        implements SysRoleMenuService {

    @Override
    public void saveRoleMenu(SysRoleMenu sysRoleMenu) {
        if (Objects.isNull(sysRoleMenu)) {
            return;
        }
        this.baseMapper.insert(sysRoleMenu);
    }
}
