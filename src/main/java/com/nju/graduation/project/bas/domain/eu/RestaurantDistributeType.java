package com.nju.graduation.project.bas.domain.eu;

/**
 * @author shanhe
 * @className RestaurantDistributeType
 * @date 2021-02-27 20:26
 **/
public enum RestaurantDistributeType {
    THIRD_PARTY(1),
    RESTAURANT_SELF(2);


    private int value;

    RestaurantDistributeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RestaurantDistributeType getRestaurantDistributeTypeByValue(int value) {
        if (THIRD_PARTY.getValue() == value) {
            return THIRD_PARTY;
        } else if (RESTAURANT_SELF.getValue() == value) {
            return RESTAURANT_SELF;
        } else {
            return null;
        }
    }
}
