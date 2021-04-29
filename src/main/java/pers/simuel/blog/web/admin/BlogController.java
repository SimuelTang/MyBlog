package pers.simuel.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pers.simuel.blog.entity.Blog;
import pers.simuel.blog.entity.User;
import pers.simuel.blog.service.TagService;
import pers.simuel.blog.service.TypeService;
import pers.simuel.blog.dto.BlogQuery;
import pers.simuel.blog.service.BlogService;

import javax.servlet.http.HttpSession;

/**
 * @Author simuel_tang
 * @Date 2021/4/27
 * @Time 10:43
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private final String BLOG_URL = "admin/blogs";
    private final String BLOG_INPUT_URL = "admin/blogs-input";
    private final String REDIRECT_URL = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 展示管理员视角的博客界面
     *
     * @param pageable  每页显示的数目
     * @param blogQuery 展示在前端的博客信息
     * @param model     数据传输
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable,
                        BlogQuery blogQuery,
                        Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return BLOG_URL;
    }

    /**
     * 点击新增博客时映射此界面
     *
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTagToModel(model);
        model.addAttribute("blog", new Blog());
        return BLOG_INPUT_URL;
    }

    /**
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String input(@PathVariable Long id, Model model) {
        setTypeAndTagToModel(model);
        Blog blog = blogService.getBlog(id);
        blog.initTagIds();
        model.addAttribute("blog", blog);
        return BLOG_INPUT_URL;
    }

    private void setTypeAndTagToModel(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    /**
     * 当点击新增按钮，编写博客内容并发布时，映射此方法
     *
     * @param blog
     * @param ra          重定向对象
     * @param httpSession 服务器端会话信息
     * @return
     */
    @PostMapping("/blogs/input")
    public String input(Blog blog, RedirectAttributes ra, HttpSession httpSession) {
        // 为当前博客设置用户，类型，标签等信息
        blog.setUser((User) httpSession.getAttribute("user")); // 设置博客所有者为当前用户
        blog.setType(typeService.getType(blog.getType().getId())); // 设置博客所属分类(Type)
        blog.setTags(tagService.listTag(blog.getTagIds())); // 设置博客所属的所有标签
        // 
        Blog b;
        if (blog.getId() == null) { // 无id，说明是新增博客
            b = blogService.saveBlog(blog);
        } else {    // 有id，说明是已存在的博客将被修改
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null) {
            ra.addFlashAttribute("message", "操作失败");
        } else {
            ra.addFlashAttribute("message", "操作成功");
        }
        // 重定位必须在第一个位置加上/，否则会以当然路径作为前缀
        return REDIRECT_URL;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_URL;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return BLOG_URL + ":: blogList";
    }

}
