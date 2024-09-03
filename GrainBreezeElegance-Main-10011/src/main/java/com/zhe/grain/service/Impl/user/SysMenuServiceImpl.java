package com.zhe.grain.service.Impl.user;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.dto.SysMenuDTO;
import com.zhe.grain.domain.user.SysMenu;
import com.zhe.grain.mapper.user.SysMenuMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.service.user.SysMenuService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.utils.SecurityUtil;
import com.zhe.grain.vo.user.SysMenuFormVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-07-25
 */
@Service
public class SysMenuServiceImpl
        extends ServiceImpl<SysMenuMapper, SysMenu>
        implements SysMenuService {

    private final UserMapper userMapper;

    @Autowired
    public SysMenuServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 分页查询权限菜单
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenu> page = this.page(
                new Query<SysMenu>().getPage(params)
        );
        List<SysMenu> records = page.getRecords();
        List<SysMenuDTO> list = records.stream()
                .map(item -> {
                    SysMenuDTO sysMenuDTO = new SysMenuDTO();
                    Long createId = item.getCreateBy();
                    Long updateId = item.getUpdateBy();
                    BeanUtil.copyProperties(item, sysMenuDTO);
                    if (createId == null || updateId == null) {
                        return sysMenuDTO;
                    }
                    String createBy = userMapper.selectById(createId).getUsername();
                    String updateBy = userMapper.selectById(updateId).getUsername();
                    sysMenuDTO.setCreateBy(createBy)
                            .setUpdateBy(updateBy);
                    return sysMenuDTO;
                }).toList();
        return new PageUtils(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    /**
     * 添加权限菜单
     *
     * @param sysMenuFormVO
     */
    @Override
    public void addPermission(SysMenuFormVO sysMenuFormVO) {
        SysMenu sysMenu = new SysMenu();
        Long id = SecurityUtil.returnUserId();
        BeanUtil.copyProperties(sysMenuFormVO, sysMenu);
        sysMenu.setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setCreateBy(id)
                .setUpdateBy(id)
                .setDelFlag(0)
                .setVisible("0")
                .setStatus("0");
        this.baseMapper.insert(sysMenu);
    }
}
