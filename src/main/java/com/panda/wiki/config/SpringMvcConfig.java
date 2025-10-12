package com.panda.wiki.config;

import com.panda.wiki.intercepter.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer { // 修正：WebMvcConfigurer

    @Resource
    private LogInterceptor logInterceptor; // 建议加上 private

    @Override // 建议加上@Override注解
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")           // 拦截所有路径
                .excludePathPatterns("/login");   // 排除登录页面
    }
}