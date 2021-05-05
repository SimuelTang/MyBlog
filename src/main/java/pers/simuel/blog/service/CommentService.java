package pers.simuel.blog.service;

import pers.simuel.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    void deleteComment(Comment comment);

    Comment findByCommentId(Long id);
}
