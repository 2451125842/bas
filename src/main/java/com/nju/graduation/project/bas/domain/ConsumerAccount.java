package com.nju.graduation.project.bas.domain;

public class ConsumerAccount {
    private Integer userId;

    private String userName;

    private String password;

    private String phone;

    private Integer lastDlyAdrsId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getLastDlyAdrsId() {
        return lastDlyAdrsId;
    }

    public void setLastDlyAdrsId(Integer lastDlyAdrsId) {
        this.lastDlyAdrsId = lastDlyAdrsId;
    }
}