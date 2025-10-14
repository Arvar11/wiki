package com.panda.wiki.req;

public class UserQueryReq extends PageReq{

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    private String loginName;

    @Override
    public String toString() {
        return "UserQueryReq{" +
                "loginName='" + loginName + '\'' +
                '}';
    }
}
