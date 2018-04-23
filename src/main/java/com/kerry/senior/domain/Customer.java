package com.kerry.senior.domain;

import java.util.Date;

/**
 * 会员实体(秒杀的用户)
 * @author Kerry Dong
 */
public class Customer {

    private Long id;
    /**手机号.*/
    private String mobilePhone;
    /**昵称.*/
    private String nickname;
    /**密码.*/
    private String password;
    /**加密的盐.*/
    private String salt;
    /**图像url.*/
    private String head;
    /**注册时间.*/
    private Date registerDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", head='" + head + '\'' +
                ", registerDate=" + registerDate +
                '}';
    }
}
