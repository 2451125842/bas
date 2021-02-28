package com.nju.graduation.project.bas.service;

/**
 * @author shanhe
 * @className PhoneService
 * @date 2021-02-26 18:28
 **/
public interface PhoneService {
    boolean isPhoneHasAccessTime(int phone, int type);
    void phoneVerifyCache(int phone, int type);
    boolean couldSendSMS_VerifyInfo(int phone, int type);
}
