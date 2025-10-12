package com.panda.wiki.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化，可以留空或添加初始化逻辑
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) 
            throws IOException, ServletException {
        
        // 转换为HttpServletRequest以获取更多信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        
        // 记录请求开始信息
        LOG.info("--- LogFilter 开始 ---");
        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("远程地址: {}", request.getRemoteAddr());
        
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            // 执行过滤链，继续后续处理
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // 记录结束时间和耗时
            long endTime = System.currentTimeMillis();
            long costTime = endTime - startTime;
            
            LOG.info("请求处理完成，耗时: {} ms", costTime);
            LOG.info("--- LogFilter 结束 ---");
        }
    }

    @Override
    public void destroy() {
        // 过滤器销毁，可以留空或添加清理逻辑
    }
}