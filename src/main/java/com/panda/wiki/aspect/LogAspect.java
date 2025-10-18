package com.panda.wiki.aspect;

import com.panda.wiki.util.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志切面 - 记录Controller请求日志
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    /** 切点定义：拦截所有controller包下的Controller类 */
    @Pointcut("execution(public * com.panda.*.controller..*Controller.*(..))")
    public void controllerPointcut() {}

    /** 前置通知：在Controller方法执行前记录日志 */
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 获取请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();

        // 打印请求日志
        LOG.info("--- 开始 ---");
        LOG.info("请求地址: {} {}", request.getMethod(), request.getRequestURL().toString());
        LOG.info("类名方法: {}.{}", signature.getDeclaringTypeName(), methodName);
        LOG.info("远程地址: {}", request.getRemoteAddr());
        
        // 设置真实IP到线程上下文
        RequestContext.setRemoteAddr(getRemoteIp(request));
    }

    /**
     * 获取真实客户端IP（处理反向代理情况）
     * @param request HTTP请求
     * @return 真实IP地址
     */
    /**
     * 获取客户端真实IP地址（处理代理服务器情况）
     * 优先级：x-forwarded-for → Proxy-Client-IP → WL-Proxy-Client-IP → getRemoteAddr
     */
    public String getRemoteIp(HttpServletRequest request) {
        // 第一步：检查反向代理头信息（如Nginx配置的代理）
        String ip = request.getHeader("x-forwarded-for");  // 最常用的代理IP头

        // 如果第一步没找到有效IP，检查Apache代理头
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");  // Apache代理服务器的IP头
        }

        // 如果前两步都没找到，检查WebLogic代理头
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");  // WebLogic代理服务器的IP头
        }

        // 如果所有代理头都无效，使用最基础的远程地址
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();  // 直接获取TCP连接地址（最后兜底方案）
        }

        return ip;
    }


}