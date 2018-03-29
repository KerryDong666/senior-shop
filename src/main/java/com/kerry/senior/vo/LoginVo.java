package com.kerry.senior.vo;

import com.kerry.senior.validator.IsMobile;

/**
 * @author CP_dongchuan
 * @date 2018/3/28
 */
public class LoginVo {

    //@NotNull
    //@IsMobile
    private String mobile;

    @IsMobile
    //@Length(min=32)
    private String password;

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "LoginVo [mobile=" + mobile + ", password=" + password + "]";
    }
}
