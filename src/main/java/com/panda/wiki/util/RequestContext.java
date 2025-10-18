package com.panda.wiki.util;
import java.io.Serializable;

/**
 * 请求上下文类，用于在线程局部变量中存储请求相关信息
 * 实现了Serializable接口，支持对象序列化
 */
public class RequestContext implements Serializable {

    // 使用ThreadLocal来存储每个线程的远程地址信息
    // ThreadLocal确保每个线程都有自己独立的变量副本，避免多线程并发问题
    // 变量命名使用下划线开头和结尾，表示这是一个特殊的重要变量
    private static ThreadLocal<String> _remoteAddr_ = new ThreadLocal<>();

    /**
     * 获取当前线程的远程地址
     * 
     * @return String 当前线程对应的远程地址，如果未设置则返回null
     */
    public static String getRemoteAddr() {
        // 从ThreadLocal中获取当前线程存储的远程地址值
        return _remoteAddr_.get();
    }

    /**
     * 设置当前线程的远程地址
     * 
     * @param remoteAddr 要设置的远程地址字符串
     */
    public static void setRemoteAddr(String remoteAddr) {
        // 将远程地址值设置到当前线程的ThreadLocal变量中
        RequestContext._remoteAddr_.set(remoteAddr);
    }
}