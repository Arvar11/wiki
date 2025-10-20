package com.panda.wiki.config;

import com.panda.wiki.intercepter.LogInterceptor;
import com.panda.wiki.intercepter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer { // 修正：WebMvcConfigurer

    @Resource
    private LogInterceptor logInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override // 建议加上@Override注解
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")           // 拦截所有路径
                .excludePathPatterns("/login").order(1);

        // 第二个拦截器：登录拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/test/**",
                        "/category/all",
                        "/ebook/list",
                        "/doc/all/**",
                        "doc/vote/**",
                        "/doc/find-content/**",
                        "/redis/**",
                        "/error",
                        "/ebook-snapshot/**"
                )
                .order(2);// 排除登录页面
    }


}