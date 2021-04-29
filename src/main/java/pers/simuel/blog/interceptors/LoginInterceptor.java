package pers.simuel.blog.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 11:19
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * @param request
     * @param response
     * @param handler
     * @return 请求能否不被拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
