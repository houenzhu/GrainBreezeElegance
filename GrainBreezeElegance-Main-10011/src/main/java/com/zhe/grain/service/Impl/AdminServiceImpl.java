package com.zhe.grain.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.TimeUtil;
import com.zhe.grain.constant.CookieConstant;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.entity.AdminEntity;
import com.zhe.grain.exception.CaptchaException;
import com.zhe.grain.exception.LoginException;
import com.zhe.grain.mapper.AdminMapper;
import com.zhe.grain.service.AdminService;
import com.zhe.grain.utils.CookieUtil;
import com.zhe.grain.utils.MD5Util;
import com.zhe.grain.utils.UUIDUtil;
import com.zhe.grain.vo.AdminLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminEntity>
        implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 处理管理员登录的业务逻辑
     * @param adminLoginVO 只需要手机号和密码
     * @return 管理员票据, 时长为半小时
     */
    @Override
    public String adminLogin(AdminLoginVO adminLoginVO, HttpServletRequest request,
                             HttpServletResponse response) {
        // 1. 先判断是否为空对象
        if (adminLoginVO == null) {
            return null;
        }
        String phone = adminLoginVO.getPhone();
        String password = adminLoginVO.getPassword();
        // 2. 判断对象的电话号码和密码是否为空
        if (!StringUtils.hasText(phone) || !StringUtils.hasText(password)) {
            return null;
        }
        boolean checkCaptcha = checkCaptcha(adminLoginVO.getCaptcha(), request);
        // 验证码不通过
        if (!checkCaptcha) {
            throw new CaptchaException();
        }
        // 3. 通过电话号码查询数据库，返回的对象是否为空
        AdminEntity adminByPhone = adminMapper.selectOne(
                new QueryWrapper<AdminEntity>().eq("phone", phone)
        );
        if (adminByPhone == null) {
            return null;
        }
        // 4. 传过来的密码明文转变成密文之后跟数据库进行对比
        // 4.1 盐值通过数据库查询得到
        String passToDB = MD5Util.inputToDBPass(password, adminByPhone.getSlat());
        if (!passToDB.equals(adminByPhone.getPassword())) {
            // 密码不正确
            return null;
        }
        // 5. 密码正确后返回一个token，并且放入redis，有效时间1h
        String ticket = RedisConstant.USER_TICKET_POSTFIX + UUIDUtil.uuid();
        Integer adminId = adminByPhone.getId();
        log.info("管理员，id{}登录成功, 生成的唯一ticket: {}", adminId, ticket);

        // 把ticket存入cookie
//        CookieUtil.setCookie(request, response, CookieConstant.COOKIE_NAME, ticket);

        // 存放用户的实体信息进redis, 一小时后过期
        redisTemplate.opsForValue().set(RedisConstant.USER_ENTITY_KEY + ticket, adminByPhone,
                60 * 60 * 1000, TimeUnit.MILLISECONDS);

        return ticket;
    }

    private boolean checkCaptcha(String captcha, HttpServletRequest request) {
        if (!StringUtils.hasText(captcha)) {
            return false;
        }
        String captchaInSession = (String) request.getSession().getAttribute("happy-captcha");
        log.info("session的验证码 = {}", captchaInSession);
        return captcha.equalsIgnoreCase(captchaInSession);
    }

    /**
     * 通过请求头的User-Ticket获取admin对象
     * @param header
     * @return
     */
    @Override
    public AdminEntity getAdminByHeader(String header) {
        log.info("header: {}", header);
        return (AdminEntity) redisTemplate.opsForValue()
                .get(RedisConstant.USER_ENTITY_KEY + header);
    }

    /**
     * 管理员注销登录接口
     * @param token
     * @return
     */
    @Override
    public boolean logout(String token) {
        // 1. 先从redis查询是否有该用户, 没有则直接返回false
        if (!StringUtils.hasText(token)) {
            return false;
        }
        String key = RedisConstant.USER_ENTITY_KEY + token;
        AdminEntity adminEntity = (AdminEntity) redisTemplate.opsForValue().get(key);
        if (null == adminEntity) {
            return false;
        }
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }
}
