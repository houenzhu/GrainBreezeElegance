package com.zhe.grain.controller;

import com.zhe.grain.annotation.AdminLoginAnnotation;
import com.zhe.grain.service.TongYiService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.Message;
import com.zhe.grain.vo.TongYiResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    @AdminLoginAnnotation
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
