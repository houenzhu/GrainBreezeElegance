package com.zhe.grain.utils;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.zhe.grain.vo.TongYiResultVO;

import java.util.List;


/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class AIUtil {

    public static GenerationParam createGenerationParam(List<Message> messages) {
        return GenerationParam.builder()
                .model("qwen-turbo")
                .messages(messages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .build();
    }

    public static GenerationResult callGenerationWithMessages(GenerationParam param)
            throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        return gen.call(param);
    }

    public static Message createMessage(Role role, String content) {
        return Message.builder().role(role.getValue()).content(content).build();
    }

    /**
     * 构造vo对象
     * @param result
     * @return
     */
    public static TongYiResultVO ResultToVO(GenerationResult result) {
        GenerationOutput.Choice choice = result.getOutput().getChoices().get(0);
        TongYiResultVO resultVO = new TongYiResultVO();
        resultVO.setRequestId(result.getRequestId())
                .setInputTokens(result.getUsage().getInputTokens())
                .setOutputTokens(result.getUsage().getOutputTokens())
                .setTotalTokens(result.getUsage().getTotalTokens())
                .setFinishReason(choice.getFinishReason())
                .setContent(choice.getMessage().getContent())
                .setRole(choice.getMessage().getRole());
        return resultVO;
    }
}
