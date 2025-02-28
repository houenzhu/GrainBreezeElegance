package com.zhe.grain.service.comment;

import com.zhe.grain.domain.comment.Comment;
import com.zhe.grain.record.CommentRecord;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface CommentService {
    List<Comment> getAllOneComment(Map<String, Object> params);

    void insertComment(CommentRecord commentRecord);
}
