package com.nju.graduation.project.bas.domain.eu;

/**
 * @author shanhe
 * @className DurationType
 * @date 2020-09-23 16:38
 **/
public enum DurationType {

    YESTERDAY(1),
    LAST_7DAYS(7),
    LAST_30DAYS(30),
    USER_DEFINED(-1);

    private int value;

    DurationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static DurationType getDurationTypeByValue(int value) {
        if (value == YESTERDAY.getValue()) {
            return YESTERDAY;
        } else if (value == LAST_7DAYS.getValue()) {
            return LAST_7DAYS;
        } else if (value == LAST_30DAYS.getValue()) {
            return LAST_30DAYS;
        } else if (value == USER_DEFINED.getValue()) {
            return USER_DEFINED;
        } else {
            return null;
        }
    }
}
