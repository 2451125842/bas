package com.nju.graduation.project.bas.domain.eu;

/**
 * @author shanhe
 * @className UserType
 * @date 2021-02-26 17:41
 **/
public enum UserType {

    CONSUMER(1),
    MANAGER(2);

    private int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserType getUserTypeByValue(int value) {
        if (value == CONSUMER.getValue()) {
            return CONSUMER;
        } else if (value == MANAGER.getValue()) {
            return MANAGER;
        }
        return null;
    }
}
