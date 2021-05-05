package pers.simuel.blog.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import pers.simuel.blog.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort updateTime);

    List<Comment> findByBlogId(Long blogId);

    Comment findCommentById(Long commentId);
}
