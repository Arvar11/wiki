package com.panda.wiki.intercepter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 拆出header的token参数
        String token = request.getHeader("token");
        log.info("登录校验开始，token：{}", token);

        if (token == null || token.isEmpty()) {
            log.info("token为空，请求被拦截");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            returnJson(response, "用户未登录", 401);
            return false;
        }

        Object object = redisTemplate.opsForValue().get(token);
        if (object == null) {
            log.warn("token无效，请求被拦截");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            returnJson(response, "用户登录已过期，请重新登录", 401);
            return false;
        } else {
            log.info("已登录：{}", object);

            // 将用户信息存入request，方便后续Controller使用
            request.setAttribute("loginUser", object);
            request.setAttribute("token", token);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求处理之后调用
        log.info("LoginInterceptor 处理完成");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 整个请求完成之后调用
        log.info("LoginInterceptor 结束");
    }

    /**
     * 返回JSON格式的错误信息
     */
    private void returnJson(HttpServletResponse response, String message, int code) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", message);
        result.put("code", code);
        result.put("data", null);

        PrintWriter writer = response.getWriter();
        writer.print(objectMapper.writeValueAsString(result));
        writer.close();
    }
}
