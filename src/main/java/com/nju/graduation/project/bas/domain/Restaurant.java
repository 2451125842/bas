package com.nju.graduation.project.bas.domain;

import java.util.Date;

public class Restaurant {
    private Integer restaurantId;

    private Integer managerId;

    private String name;

    private String headPortrait;

    private Integer secondTypeId;

    private String restaurantPhone;

    private String city;

    private Integer cityId;

    private String adcode;

    private String address;

    private String longitude;

    private String latitude;

    private Date startTime;

    private Date endTime;

    private Integer businessState;

    private Integer distributeType;

    private Integer auditType;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait == null ? null : headPortrait.trim();
    }

    public Integer getSecondTypeId() {
        return secondTypeId;
    }

    public void setSecondTypeId(Integer secondTypeId) {
        this.secondTypeId = secondTypeId;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone == null ? null : restaurantPhone.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode == null ? null : adcode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getBusinessState() {
        return businessState;
    }

    public void setBusinessState(Integer businessState) {
        this.businessState = businessState;
    }

    public Integer getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(Integer distributeType) {
        this.distributeType = distributeType;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }
}