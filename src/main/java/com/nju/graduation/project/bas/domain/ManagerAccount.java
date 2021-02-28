package com.nju.graduation.project.bas.domain;

public class ManagerAccount {
    private Integer managerId;

    private String nakename;

    private String password;

    private String phone;

    private Boolean realNameAuthentication;

    private Integer lastLoginRestaurant;

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getNakename() {
        return nakename;
    }

    public void setNakename(String nakename) {
        this.nakename = nakename == null ? null : nakename.trim();
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

    public Boolean getRealNameAuthentication() {
        return realNameAuthentication;
    }

    public void setRealNameAuthentication(Boolean realNameAuthentication) {
        this.realNameAuthentication = realNameAuthentication;
    }

    public Integer getLastLoginRestaurant() {
        return lastLoginRestaurant;
    }

    public void setLastLoginRestaurant(Integer lastLoginRestaurant) {
        this.lastLoginRestaurant = lastLoginRestaurant;
    }
}