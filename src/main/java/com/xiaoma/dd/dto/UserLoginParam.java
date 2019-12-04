package com.xiaoma.dd.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class UserLoginParam {

    @NotEmpty(message = "用户名不能为空")
    private String phone;
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
