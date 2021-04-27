package pers.simuel.web.admin;

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
import pers.simuel.entity.Type;
import pers.simuel.service.TypeService;

/**
 * 前端路由，负责对URL对应的方法进行映射，
 * 所有为Get的映射都是负责显示
 * 为Post的映射负责提交数据
 *
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 11:29
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 当点记分类的时候会映射此方法
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 在分类页点记新增时映射此方法
     *
     * @param model
     * @return 新增分类的显示地址
     */
    @GetMapping("/types/input")
    public String input(Model model) {
        // 新增分类页面涉及到对原有数据的取值，所以会用到Type对象，为了防止报错，实例化一个空对象
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 在分类页点击一个已存在的分类时映射此方法
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String input(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }


    /**
     * 点击了新增链接，并提交新的Type时会映射此方法
     *
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String types(Type type, BindingResult result, RedirectAttributes attributes) {
        // 先检测这个Type是否已经存在了
        Type typeToBeChecked = typeService.getTypeByName(type.getName());
        if (typeToBeChecked != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        // 检测通过，将Type加入数据库
        Type typeToBeSaved = typeService.saveType(type);
        if (typeToBeSaved != null) {
            attributes.addAttribute("message", "新增成功");
        } else {
            attributes.addAttribute("message", "新增失败");
        }
        return "redirect:/admin/types";
    }

    /**
     * 对
     *
     * @return
     */
    @PostMapping("/types/{id}")
    public String update(Type type, BindingResult result,
                         @PathVariable Long id, RedirectAttributes attributes) {
        // 先检测这个Type是否已经存在了
        Type typeToBeChecked = typeService.getTypeByName(type.getName());
        if (typeToBeChecked != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        // 检测通过，将Type加入数据库
        Type typeToBeSaved = typeService.updateType(id, type);
        if (typeToBeSaved != null) {
            attributes.addAttribute("message", "更新成功");
        } else {
            attributes.addAttribute("message", "更新失败");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
