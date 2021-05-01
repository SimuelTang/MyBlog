package pers.simuel.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pers.simuel.blog.entity.Comment;
import pers.simuel.blog.entity.User;
import pers.simuel.blog.service.BlogService;
import pers.simuel.blog.service.CommentService;

import javax.servlet.http.HttpSession;

/**
 * @Author simuel_tang
 * @Date 2021/4/29
 * @Time 9:14
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String comment(Comment comment, HttpSession session) {
        // 获取当前评论对应的博客id（所有的评论区域都有一个隐含的blogId和parentCommentId）
        Long blogId = comment.getBlog().getId();
        // 为当前评论设置对应博客的具体内容
        comment.setBlog(blogService.getBlog(blogId));
        // 获取当前服务器中的user
        User user = (User) session.getAttribute("user");
        if (user != null) { // 如果不为空，说明是管理员
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {    // 普通游客
            comment.setAvatar(avatar);
        }
        // 评论保存至数据库
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
