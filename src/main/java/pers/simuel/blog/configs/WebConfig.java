package pers.simuel.blog.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.simuel.blog.interceptors.LoginInterceptor;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 11:21
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin") // 页面本身不能被拦截，否则会一直重定向
                .excludePathPatterns("/admin/login"); // 登录的请求不能被拦截，否则数据无法传送
    }
}
