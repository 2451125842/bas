package com.nju.graduation.project.bas.domain.eu;

/**
 * @author shanhe
 * @className UserSex
 * @date 2021-02-27 10:03
 **/
public enum UserSex {
    MAN(1),
    WOMEN(2);

    private int value;

    UserSex(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static UserSex getUserSexByValue(int value) {
        if (value == MAN.getValue()) {
            return MAN;
        } else if (value == WOMEN.getValue()) {
            return WOMEN;
        } else {
            return null;
        }
    }
}
