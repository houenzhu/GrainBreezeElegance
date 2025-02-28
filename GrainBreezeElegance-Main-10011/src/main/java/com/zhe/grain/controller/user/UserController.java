package com.zhe.grain.controller.user;

import com.zhe.grain.annotation.CheckArgs;
import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.dto.UserDTO;
import com.zhe.grain.exception.vaild.SaveGroup;
import com.zhe.grain.service.user.UserService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.ResultMsgEnum;
import com.zhe.grain.vo.user.RegisterUserVO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 对用户管理和权限管理进行对外开发http接口
 */

@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "manage/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 分页显示所有用户信息
     * @param params
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAuthority('sys:user:list')")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = userService.queryPage(params);
        return Result.success(pageUtils);
    }

    /**
     * 添加管理员或者用户信息
     */
    @PostMapping("/save")
    @PreAuthorize("@zhe.hasAuthority('sys:user:save')")
    @CheckArgs
    public Result<Boolean> save(@RequestBody @Validated(SaveGroup.class) RegisterUserVO registerUserVO) {
        boolean flag = userService.saveUser(registerUserVO);
        return flag ? Result.success(ResultMsgEnum.SUCCESS, "添加成功!", true)
                : Result.error(ResultMsgEnum.ERROR,
                "用户名已经存在, 请重新添加", false);
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("@zhe.hasAuthority('sys:user:findOne')")
    public Result<UserDTO> findById(@PathVariable("id") Long id) {
        return Result.success(userService.getUserById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("@zhe.hasAuthority('sys:user:update')")
    @CheckArgs
    public Result<Boolean> update(@RequestBody RegisterUserVO userVO, @PathVariable("id") Long id) {
        userService.updateUser(userVO, id);
        return Result.success();
    }

}
