package pers.simuel.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.simuel.blog.dao.CommentRepository;
import pers.simuel.blog.entity.Comment;
import pers.simuel.blog.service.CommentService;

import java.util.ArrayList;
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

    /**
     * 根据博客id找出这篇博客下对应的评论
     *
     * @param blogId
     * @return
     */
    @Transactional
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, Sort.by("createTime"));
        formatComments(comments);
        return comments;
    }

    /**
     * @param comment 新发表的评论
     * @return
     */
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        // 保存前，先判断这条评论是否为父级评论（默认id设置为-1）
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) { // 不是一级评论
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public Comment findByCommentId(Long commentId) {
        return commentRepository.findCommentById(commentId);
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {    // 不是一级评论
            deleteSelf(comment);
        } else {    // 一级评论，删除其本身和所有子评论
            deleteSelfAndSubs(comment);
        }
    }
    
    private void deleteSelfAndSubs(Comment comment) {
    }

    private void deleteSelf(Comment comment) {
        // 找到这条评论的所有一级回复，将这些回复的parentId置空
        List<Comment> replyComments = comment.getReplyComments();
        for (Comment replyComment : replyComments) {
            replyComment.setParentComment(null);
        }
        // 删除这条评论
        commentRepository.delete(comment);
    }


    /**
     * 对某篇博客下的所有评论进行格式化
     *
     * @param comments
     */
    private void formatComments(List<Comment> comments) {
        // 对所有的顶级评论，找到它们的所有回复
        for (Comment comment : comments) {
            // 获取顶级回复
            List<Comment> replyComments = comment.getReplyComments();
            // 对于这些顶级回复，递归获取子级回复
            List<Comment> subComments = new ArrayList<>();
            for (Comment replyComment : replyComments) {
                findAllSubComments(replyComment, subComments);
            }
            // 所有回复，相对与顶级评论，设置为同一级
            comment.setReplyComments(subComments);
        }
    }

    private void findAllSubComments(Comment replyComment, List<Comment> subComments) {
        subComments.add(replyComment);
        if (replyComment.getReplyComments().size() > 0) {
            for (Comment reply : replyComment.getReplyComments()) {
                findAllSubComments(reply, subComments);
            }
        }
    }
}
