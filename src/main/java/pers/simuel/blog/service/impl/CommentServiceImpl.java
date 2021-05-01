package pers.simuel.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.simuel.blog.dao.CommentRepository;
import pers.simuel.blog.entity.Comment;
import pers.simuel.blog.service.CommentService;

import java.util.Date;
import java.util.List;

/**
 * @Author simuel_tang
 * @Date 2021/4/30
 * @Time 13:11
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId, Sort.by("createTime"));
        return comments;
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        // 保存前，判断这条评论是否为父级评论
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) { // 不是父级评论
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
