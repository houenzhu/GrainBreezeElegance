package com.zhe.grain.controller.comment;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.comment.Comment;
import com.zhe.grain.record.CommentRecord;
import com.zhe.grain.service.comment.CommentService;
import com.zhe.grain.utils.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@RestController
@RequestMapping(ControllerConstant.API_PREFIX + "comment")
@AllArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    /**
     * 全部一级评论
     * @return
     */
    @GetMapping("/all_one_comment")
    public Result<List<Comment>> allOneComment(@RequestParam("params") Map<String, Object> params) {
        return Result.success(commentService.getAllOneComment(params));
    }

    @PostMapping("/comment")
    public Result<Object> comment(@RequestBody CommentRecord commentRecord) {
        commentService.insertComment(commentRecord);
        return Result.success("评论成功!", null);
    }
}
