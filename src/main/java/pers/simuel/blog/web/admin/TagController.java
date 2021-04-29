package pers.simuel.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pers.simuel.blog.entity.Tag;
import pers.simuel.blog.service.TagService;

import javax.validation.Valid;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 14:29
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    private final String TAGS_INPUT_URL = "admin/tags-input";
    private final String TAGS_REDIRECT_URL = "redirect:/admin/tags";

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return TAGS_INPUT_URL;
    }

    @GetMapping("/tags/{id}/input")
    public String input(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return TAGS_INPUT_URL;
    }

    @PostMapping("/tags")
    public String input(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tagToBeAdded = tagService.getTagByName(tag.getName());
        if (tagToBeAdded != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return TAGS_INPUT_URL;
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return TAGS_REDIRECT_URL;
    }


    @PostMapping("/tags/{id}")
    public String update(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Tag tagToBeUpdated = tagService.getTagByName(tag.getName());
        if (tagToBeUpdated != null) {   // 待更新的标签已经存在
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return TAGS_INPUT_URL;
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return TAGS_REDIRECT_URL;
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return TAGS_REDIRECT_URL;
    }
}
