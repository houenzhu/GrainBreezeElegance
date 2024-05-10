package com.zhe.grain.service.Impl;

import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.exception.AIException;
import com.zhe.grain.service.TongYiService;
import com.zhe.grain.utils.AIUtil;
import com.zhe.grain.utils.ThreadLocalUtil;
import com.zhe.grain.vo.TongYiResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
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
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public TongYiResultVO askTongYi(String message) {
        TongYiResultVO resultVO = null;
        List<Message> messages;
        String key = RedisConstant.TONG_YI_HISTORY
                + ThreadLocalUtil.getThreadLocalVal().getId();
        try {
            // "chat:history:1"
            messages = (List<Message>) redisTemplate.opsForValue().get(key);
            if (CollectionUtils.isEmpty(messages)) {
                messages = new ArrayList<>();
                messages.add(AIUtil.createMessage(Role.SYSTEM, "You are a helpful assistant."));
                // 30分钟会话过期
                redisTemplate.opsForValue().set(key, messages, 30, TimeUnit.MINUTES);
            }
            if (!"exit".equalsIgnoreCase(message)) {
                messages.add(AIUtil.createMessage(Role.USER, message));
                GenerationParam param = AIUtil.createGenerationParam(messages);
                GenerationResult result = AIUtil.callGenerationWithMessages(param);
                resultVO = AIUtil.ResultToVO(result);
                System.out.println("模型输出：" + result.getOutput().getChoices().get(0).getMessage().getContent());
                messages.add(result.getOutput().getChoices().get(0).getMessage());
                redisTemplate.opsForValue().set(key, messages, 30, TimeUnit.MINUTES);
            }
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
            throw new AIException("AI对话接口出现异常...");
        }
        return resultVO;
    }
}
