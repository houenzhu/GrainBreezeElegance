package com.zhe.grain.service.Impl.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.entity.WeixinUserEntity;
import com.zhe.grain.mapper.user.WeixinUserMapper;
import com.zhe.grain.service.user.WeixinUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@Slf4j
public class WeixinUserServiceImpl extends ServiceImpl<WeixinUserMapper, WeixinUserEntity>
        implements WeixinUserService {

}
