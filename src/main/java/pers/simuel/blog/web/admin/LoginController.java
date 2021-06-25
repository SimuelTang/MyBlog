package pers.simuel.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pers.simuel.blog.entity.User;
import pers.simuel.blog.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 11:04
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    private final String ADMIN_REDIRECT_URL = "redirect:/admin";

    @Autowired
    private UserService userService;

    /**
     * 只输入 /admin 或输入 /admin/login 时跳转到登录页面
     *
     * @return
     */
    @GetMapping(value = {"", "/login"})
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        HttpServletResponse response) {
        User user = userService.checkUser(username, password);
        if (user != null) { // 判断用户是否存在
//            Cookie cookie = new Cookie(username, password);
//            cookie.setMaxAge(7 * 24 * 60 * 60); // 7天过期
//            response.addCookie(cookie);
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return ADMIN_REDIRECT_URL;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return ADMIN_REDIRECT_URL;
    }

    @GetMapping("/index")
    public String index() {
        return "admin/index";
    }

}
