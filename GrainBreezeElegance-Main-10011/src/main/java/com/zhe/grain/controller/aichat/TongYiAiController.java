package com.zhe.grain.controller.aichat;

import com.zhe.grain.service.aichat.TongYiService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.tongyi.Message;
import com.zhe.grain.vo.tongyi.TongYiResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 接入通义千问gpt
 */

@RestController
@RequestMapping("/grain/ai")
public class TongYiAiController {

    @Autowired
    private TongYiService tongYiService;

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
}
