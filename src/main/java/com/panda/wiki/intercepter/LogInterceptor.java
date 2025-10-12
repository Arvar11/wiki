package com.panda.wiki.intercepter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LogInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 只在进入Controller之前执行
        LOG.info("--- LogInterceptor 开始 ---");
        LOG.info("拦截器 - 请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("拦截器 - 处理器: {}", handler.toString());
        
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        return true; // 继续执行后续拦截器和控制器
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // Controller执行之后，视图渲染之前
        long startTime = (long) request.getAttribute("requestStartTime");
        long costTime = System.currentTimeMillis() - startTime;
        LOG.info("拦截器 - Controller执行完成，耗时: {} ms", costTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 整个请求完成之后（包括视图渲染）
        long startTime = (long) request.getAttribute("requestStartTime");
        long totalTime = System.currentTimeMillis() - startTime;
        LOG.info("拦截器 - 请求完全结束，总耗时: {} ms", totalTime);
        LOG.info("--- LogInterceptor 结束 ---");
        
        if (ex != null) {
            LOG.error("请求处理过程中出现异常: ", ex);
        }
    }
}