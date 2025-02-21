package com.zhe.grain.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.domain.SysUser;
import com.zhe.grain.dto.UserDTO;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.vo.user.RegisterUserVO;

import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 对用户进行管理
 */

public interface UserService extends IService<SysUser> {
    /**
     * 定义一个可以返回全部用户分页信息的接口
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 注册或者添加用户
     */
    boolean saveUser(RegisterUserVO registerUserVO);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    UserDTO getUserById(Long id);


    /**
     * 用户更新
     * @return
     */
    void updateUser(RegisterUserVO userVO, Long id);

}
