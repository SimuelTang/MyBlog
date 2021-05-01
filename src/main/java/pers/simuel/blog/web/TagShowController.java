package pers.simuel.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.simuel.blog.entity.Tag;
import pers.simuel.blog.service.BlogService;
import pers.simuel.blog.service.TagService;

import java.util.List;

/**
 * @Author simuel_tang
 * @Date 2021/5/1
 * @Time 16:49
 */
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{tagId}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long tagId, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (tagId == -1) {
            tagId = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(tagId, pageable));
        model.addAttribute("activeTagId", tagId);
        return "tags";
    }
}
