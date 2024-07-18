package com.zhe.grain.service.Impl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.entity.LoginUser;
import com.zhe.grain.entity.SysUser;
import com.zhe.grain.mapper.user.LoginUserMapper;
import com.zhe.grain.service.user.LoginUserService;
import com.zhe.grain.utils.*;
import com.zhe.grain.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@Slf4j
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser>
        implements LoginUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result<Object> login(AdminLoginVO adminLoginVO, HttpServletRequest request) {
        Object captchaObject = request.getSession().getAttribute("happy-captcha");
        // 防止使用postman之类的工具绕过验证码
        if (Objects.isNull(captchaObject)) {
            return Result.error(ResultMsgEnum.LOGIN_ERROR, "服务器有误!", null);
        }
        String captcha = (String) captchaObject;
        String userCaptcha = adminLoginVO.getCaptcha();
        if (!this.checkCaptcha(captcha, userCaptcha)) {
            return Result.error(ResultMsgEnum.LOGIN_ERROR, "请检查验证码是否有误!", null);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(adminLoginVO.getUsername(),
                        adminLoginVO.getPassword());
        // 委托者
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            return Result.error(ResultMsgEnum.LOGIN_ERROR, null);
        }
        //如果认证通过，使用user生成jwt  jwt存入ResponseResult 返回
        //如果认证通过，拿到这个当前登录用户信息
        Map<String, String> map = this.createAndReturnJwt(authenticate);
        return Result.success(ResultMsgEnum.LOGIN_SUCCESS, map);
    }

    /**
     * 注销
     * @return
     */
    @Override
    public Result<Object> logout() {
        LoginUser loginUser = (LoginUser) SecurityUtil.returnPrincipal();
        redisCache.deleteObject(RedisConstant.USER_ENTITY_KEY + loginUser.getSysUser().getId());
        return Result.success(ResultMsgEnum.LOGOUT_SUCCESS, null);
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public Result<SysUser> getUserInfo() {
        LoginUser user = (LoginUser) SecurityUtil.returnPrincipal();
        if (Objects.isNull(user)) {
            return Result.error(ResultMsgEnum.UNAUTHORIZED, "登录信息有误或者失效, 请重新登录",
                    null);
        }
        return Result.success(user.getSysUser());
    }

    /**
     * 验证码校验
     * @param captcha
     * @param userCaptcha
     * @return
     */
    private boolean checkCaptcha(String captcha, String userCaptcha) {
        if (StringUtils.isBlank(userCaptcha)){
            return false;
        }
        return userCaptcha.equals(captcha);
    }

    private Map<String, String> createAndReturnJwt(Authentication authenticate) {
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSysUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", RedisConstant.USER_TICKET_POSTFIX + jwt);
        redisCache.setCacheObject(RedisConstant.USER_ENTITY_KEY + userId,
                loginUser, 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        return map;
    }
}
