package com.panda.wiki.exception;

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("登录名已存在"),
    LOGIN_USER_ERROR("登录名不存在或密码错误"),
    ID_NOT_EXIST("id不存在");
    // 可以继续添加其他异常码
    // EXAMPLE_ERROR("示例错误");

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}