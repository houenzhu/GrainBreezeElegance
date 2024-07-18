package com.zhe.grain.service.Impl.aichat;

import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.entity.LoginUser;
import com.zhe.grain.exception.AIException;
import com.zhe.grain.service.aichat.TongYiService;
import com.zhe.grain.utils.AIUtil;
import com.zhe.grain.utils.RedisCache;
import com.zhe.grain.utils.SecurityUtil;
import com.zhe.grain.utils.ThreadLocalUtil;
import com.zhe.grain.vo.TongYiResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
public class TongYiServiceImpl implements TongYiService {

    @Autowired
    private RedisCache redisCache;

    /**
     * 聊天
     *
     * @param message
     * @return
     */
    @Override
    public TongYiResultVO askTongYi(String message) {
        TongYiResultVO resultVO = null;
        List<Message> messages;
        LoginUser loginUser = (LoginUser) SecurityUtil.returnPrincipal();
        String key = RedisConstant.TONG_YI_HISTORY + loginUser.getSysUser().getId();
        try {
            // "chat:history:1"
            messages = redisCache.getCacheObject(key);
            if (CollectionUtils.isEmpty(messages)) {
                messages = new ArrayList<>();
                messages.add(AIUtil.createMessage(Role.SYSTEM, "You are a helpful assistant."));
                // 30分钟会话过期
                redisCache.setCacheObject(key, messages, 30, TimeUnit.MINUTES);
            }
            if (!"exit".equalsIgnoreCase(message)) {
                messages.add(AIUtil.createMessage(Role.USER, message));
                GenerationParam param = AIUtil.createGenerationParam(messages);
                GenerationResult result = AIUtil.callGenerationWithMessages(param);
                resultVO = AIUtil.ResultToVO(result);
                System.out.println("模型输出：" + result.getOutput().getChoices()
                        .get(0).getMessage().getContent());
                messages.add(result.getOutput().getChoices().get(0).getMessage());
                redisCache.setCacheObject(key, messages, 30, TimeUnit.MINUTES);
            }
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
            throw new AIException("AI对话接口出现异常...");
        }
        return resultVO;
    }
}
