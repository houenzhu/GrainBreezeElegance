package com.zhe.grain.service.Impl.user;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.dto.UserDTO;
import com.zhe.grain.domain.SysUser;
import com.zhe.grain.domain.user.SysUserRole;
import com.zhe.grain.mapper.user.SysUserRoleMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.service.user.UserService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.utils.SecurityUtil;
import com.zhe.grain.vo.user.RegisterUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser>
        implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final SysUserRoleMapper sysUserRoleMapper;
    private static final String DEFAULT_USER_AVATAR =
            "https://zheliving-10000.oss-cn-guangzhou.aliyuncs.com/%E5%8F%AF%E7%88%B1%E8%B6%85%E4%BA%BA%E8%B4%B4%E7%BA%B8.png";

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           SysUserRoleMapper sysUserRoleMapper) {
        this.passwordEncoder = passwordEncoder;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    /**
     * 用户信息分页查询
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        IPage<SysUser> page = this.page(
                new Query<SysUser>().getPage(params),
                lambdaQueryWrapper
        );
        List<SysUser> records = page.getRecords();
        List<UserDTO> list = records.stream()
                .map(record -> {
                    UserDTO userVO = new UserDTO();
                    BeanUtils.copyProperties(record, userVO);
                    // 被谁创建
                    Long createBy = record.getCreateBy();
                    String createName = this.baseMapper.selectById(createBy).getUsername();
                    userVO.setCreateBy(createName);
                    // 被谁修改
                    Long updateBy = record.getUpdateBy();
                    String updateName = this.baseMapper.selectById(updateBy).getUsername();
                    userVO.setUpdateBy(updateName);
                    return userVO;
                }).toList();
        return new PageUtils(list, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    /**
     * 保存用户, 还要添加角色
     */
    @Override
    @Transactional
    public boolean saveUser(RegisterUserVO registerUserVO) {
        SysUser sysUser = new SysUser();
        String username = sysUser.getUsername();
        if (!checkUserUnique(username)) { // 不为空
            return false;
        }
        String userType = registerUserVO.getUserType();
        BeanUtil.copyProperties(registerUserVO, sysUser);
        // 默认初始密码123456
        String encodePassword = passwordEncoder.encode("123456");
        sysUser.setCreateBy(SecurityUtil.returnUserId())
                .setUpdateBy(SecurityUtil.returnUserId())
                .setAvatar(DEFAULT_USER_AVATAR)
                .setStatus("0")
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setDelFlag(0)
                .setPassword(encodePassword);
        try {
            this.baseMapper.insert(sysUser);
            // 同时增加新增用户的角色关系
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            if ("0".equals(userType)) {
                sysUserRole.setRoleId(1L);
            } else {
                sysUserRole.setRoleId(2L);
            }
            sysUserRoleMapper.insert(sysUserRole);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkUserUnique(String username) {
        List<SysUser> sysUsers = this.baseMapper.selectList(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getUsername, username)
        );
        return CollectionUtils.isEmpty(sysUsers);
    }
}
