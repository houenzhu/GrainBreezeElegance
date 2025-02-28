package com.zhe.grain.controller.aichat;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.service.aichat.TongYiService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.tongyi.Message;
import com.zhe.grain.vo.tongyi.TongYiResultVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 接入通义千问gpt
 */

@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "/ai")
@AllArgsConstructor
@Slf4j
public class TongYiAiController {


    private TongYiService tongYiService;
    private OllamaChatModel chatModel;
    private final ChatClient chatClient;

    @Autowired
    public TongYiAiController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @PostMapping("/chat")
    @PreAuthorize("hasAnyAuthority('sys:ai:chat', 'weixin:ai:chat')")
    public Result<Object> askTongYi(@RequestBody Message message) {
        try {
            TongYiResultVO resultVO = tongYiService.askTongYi(message.getMessage());
            if (resultVO == null) {
                return Result.error();
            }
            return Result.success(resultVO);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * @param message
     * @return
     */
    @GetMapping("/deepseek")
    public ResponseEntity<Flux<String>> askDeepSeek(@RequestParam("message") String message) {
        try {
            Flux<String> content = chatClient.prompt(message).stream().content();
            return ResponseEntity.ok().contentType(new MediaType(MediaType.TEXT_EVENT_STREAM)).body(content);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(Flux.just("error"));
        }
    }
}
