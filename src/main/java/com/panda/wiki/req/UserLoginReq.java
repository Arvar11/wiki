package com.panda.wiki.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserLoginReq {

    @NotEmpty(message = "【用户名】不能为空")
    private String loginName;

    @NotNull(message = "【密码】不能为空")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{6,20}$",
            message = "【密码】至少包含数字和英文，长度6-20")
    private String password;

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public @NotEmpty(message = "【用户名】不能为空") String getLoginName() {
        return loginName;
    }

    public void setLoginName(@NotEmpty(message = "【用户名】不能为空") String loginName) {
        this.loginName = loginName;
    }

    public @NotNull(message = "【密码】不能为空") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{6,20}$",
            message = "【密码】至少包含数字和英文，长度6-20") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "【密码】不能为空") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{6,20}$",
            message = "【密码】至少包含数字和英文，长度6-20") String password) {
        this.password = password;
    }
}
