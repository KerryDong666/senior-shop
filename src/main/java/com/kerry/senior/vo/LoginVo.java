package com.kerry.senior.vo;

import com.kerry.senior.validator.IsMobile;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Kerry Dong
 */
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobilePhone;

    //@Length(min=32)
    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobilePhone='" + mobilePhone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
