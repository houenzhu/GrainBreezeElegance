package com.zhe.grain.service.Impl.comment;

import com.zhe.feign.UserFeign;
import com.zhe.grain.domain.comment.Comment;
import com.zhe.grain.record.CommentRecord;
import com.zhe.grain.service.comment.CommentService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final MongoTemplate mongoTemplate;
    private final UserFeign userFeign;

    public static final String COMMENT_TAG_NAME = "commentTagName";
    public static final String COMMENT_TAG_ID = "commentTagId";
    public static final String COMMENT_SUPER_ID = "commentTagId";
    public static final String COMMENT_CREATE_TIME = "commentCreateTime";
    /**
     * 查看该标签下的所有一级评论
     *
     * @param params
     */
    @Override
    public List<Comment> getAllOneComment(Map<String, Object> params) {
        Long commentTagId = (Long) params.get(COMMENT_TAG_ID);
        String commentTagName = (String) params.get(COMMENT_TAG_NAME);
        Criteria criteria = new Criteria().andOperator(Criteria.where(COMMENT_TAG_ID).is(commentTagId),
                Criteria.where(COMMENT_TAG_NAME).is(commentTagName),
                // 一级评论的父id为0
                Criteria.where(COMMENT_SUPER_ID).is("0"));
        Query query = new Query(criteria).with(Sort.by(Sort.Direction.DESC, COMMENT_CREATE_TIME));
        return mongoTemplate.find(query, Comment.class);
    }

    @Override
    public void insertComment(CommentRecord commentRecord) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentRecord, comment);
        Long userId = SecurityUtil.returnUserId();
    }
}
