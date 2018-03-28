package com.kerry.senior.domain;

import javax.validation.constraints.Min;

/**
 * @author
 * @date 2017/9/15
 */
public class User {
    private Long id;
    private String username;
    private String password;
    //表单验证
    @Min(value = 10,message = "不符合的成员类型")
    private int type;

    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
