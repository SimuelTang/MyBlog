package pers.simuel.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.simuel.blog.service.BlogService;
import pers.simuel.blog.service.TagService;
import pers.simuel.blog.service.TypeService;

/**
 * 前端首页控制器，负责展示博客的某些信息
 *
 * @Author simuel_tang
 * @Date 2021/4/28
 * @Time 9:11
 */
@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 游客进入博客首页映射此方法
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
//        model.addAttribute("page", blogService.listBlog(pageable)); // 中间的博客信息
        model.addAttribute("page", blogService.listPublishedBlog(pageable)); // 中间的博客信息
        model.addAttribute("types", typeService.listTypeTop(6)); // 侧边栏的分类信息
        model.addAttribute("tags", tagService.listTagTop(10));  // 侧边栏的标签信息
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(5));  // 显示推荐博客
        return "index";
    }

    /**
     * 点击博客后映射此方法
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    /**
     * 右上角查询框，想查询某个内容时映射此方法
     *
     * @param pageable
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}
