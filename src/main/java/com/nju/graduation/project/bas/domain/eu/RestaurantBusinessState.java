package com.nju.graduation.project.bas.domain.eu;

/**
 * @author shanhe
 * @className RestaurantBusinessState
 * @date 2021-02-27 20:25
 **/
public enum  RestaurantBusinessState {
    ON(1),
    OFF(2);

    private int value;

    RestaurantBusinessState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RestaurantBusinessState getRestaurantBusinessStateByValue(int value) {
        if (ON.getValue() == value) {
            return ON;
        } else if (OFF.getValue() == value) {
            return OFF;
        } else {
            return null;
        }
    }
}
