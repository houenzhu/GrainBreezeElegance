package com.zhe.grain.test;

import com.zhe.grain.domain.comment.Comment;
import com.zhe.grain.service.comment.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@SpringBootTest
@Slf4j
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Test
    public void m1() {
        Map<String, Object> map = new HashMap<>();
        map.put("commentTagId", 1L);
        map.put("commentTagName", "服务");
        List<Comment> allOneComment = commentService.getAllOneComment(map);
        allOneComment.forEach(System.out::println);
    }
}
