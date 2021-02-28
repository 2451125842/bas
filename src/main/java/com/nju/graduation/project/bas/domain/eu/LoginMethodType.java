package com.nju.graduation.project.bas.domain.eu;

import com.nju.graduation.project.bas.utils.SMS_VerifyUtils;

/**
 * @author shanhe
 * @className LoginMethodType
 * @date 2021-02-24 16:34
 **/
public enum LoginMethodType {

    PASSWORD(1),
    SMS(2);


    private int value;

    LoginMethodType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static LoginMethodType getLoginMethodTypeByValue(int value) {
        if (value == PASSWORD.getValue()) {
            return PASSWORD;
        } else if (value == SMS.getValue()) {
            return SMS;
        } else {
            return null;
        }
    }
}
