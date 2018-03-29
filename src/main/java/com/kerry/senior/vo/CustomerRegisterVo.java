package com.kerry.senior.vo;

import com.kerry.senior.util.UUIDUtil;
import com.kerry.senior.validator.IsMobile;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户注册对象
 * @author CP_dongchuan
 */
public class CustomerRegisterVo {

    /**手机号.*/
    @IsMobile
    private String mobilePhone;
    /**昵称.*/
    @NotNull
    private String nickname;
    /**密码.*/
    @NotNull
    private String password;
    /**加密的盐.*/
    private String salt;
    /**图像url.*/
    private String head;
    /**注册时间.*/
    private Date registerDate;

    public CustomerRegisterVo() {
        this.salt = UUIDUtil.getUuid();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
