package com.panda.wiki.resp;

/**
 * 通用响应类
 * 用于封装API接口的返回结果
 * 
 * @param <T> 响应数据的类型
 */
public class CommonResp<T> {
    
    /**
     * 业务上的成功或失败
     */
    private boolean success = true;
    
    /**
     * 返回信息
     */
    private String message;
    
    /**
     * 返回泛型数据，自定义类型
     */
    private T content;
    
    /**
     * 默认构造函数
     */
    public CommonResp() {
    }
    
    /**
     * 带成功状态的构造函数
     * 
     * @param success 是否成功
     */
    public CommonResp(boolean success) {
        this.success = success;
    }
    
    /**
     * 带消息的构造函数
     * 
     * @param message 响应消息
     */
    public CommonResp(String message) {
        this.message = message;
    }
    
    /**
     * 完整参数的构造函数
     * 
     * @param success 是否成功
     * @param message 响应消息
     * @param content 响应数据
     */
    public CommonResp(boolean success, String message, T content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }
    
    /**
     * 获取业务成功状态
     * 
     * @return 成功返回true，失败返回false
     */
    public boolean getSuccess() {
        return success;
    }
    
    /**
     * 设置业务成功状态
     * 
     * @param success 成功状态
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * 获取响应消息
     * 
     * @return 响应消息
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 设置响应消息
     * 
     * @param message 响应消息
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * 获取响应数据
     * 
     * @return 响应数据
     */
    public T getContent() {
        return content;
    }
    
    /**
     * 设置响应数据
     * 
     * @param content 响应数据
     */
    public void setContent(T content) {
        this.content = content;
    }
    
    /**
     * 创建成功响应（无数据）
     * 
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> CommonResp<T> success() {
        return new CommonResp<>(true, "操作成功", null);
    }
    
    /**
     * 创建成功响应（带消息）
     * 
     * @param <T> 数据类型
     * @param message 成功消息
     * @return 成功响应
     */
    public static <T> CommonResp<T> success(String message) {
        return new CommonResp<>(true, message, null);
    }
    
    /**
     * 创建成功响应（带数据）
     * 
     * @param <T> 数据类型
     * @param content 响应数据
     * @return 成功响应
     */
    public static <T> CommonResp<T> success(T content) {
        return new CommonResp<>(true, "操作成功", content);
    }
    
    /**
     * 创建成功响应（带消息和数据）
     * 
     * @param <T> 数据类型
     * @param message 成功消息
     * @param content 响应数据
     * @return 成功响应
     */
    public static <T> CommonResp<T> success(String message, T content) {
        return new CommonResp<>(true, message, content);
    }
    
    /**
     * 创建失败响应（无数据）
     * 
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> CommonResp<T> error() {
        return new CommonResp<>(false, "操作失败", null);
    }
    
    /**
     * 创建失败响应（带消息）
     * 
     * @param <T> 数据类型
     * @param message 错误消息
     * @return 失败响应
     */
    public static <T> CommonResp<T> error(String message) {
        return new CommonResp<>(false, message, null);
    }
    
    /**
     * 创建失败响应（带消息和数据）
     * 
     * @param <T> 数据类型
     * @param message 错误消息
     * @param content 响应数据
     * @return 失败响应
     */
    public static <T> CommonResp<T> error(String message, T content) {
        return new CommonResp<>(false, message, content);
    }
    
    @Override
    public String toString() {
        return "CommonResp{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        CommonResp<?> that = (CommonResp<?>) o;
        
        if (success != that.success) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }
    
    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}