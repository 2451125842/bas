package com.nju.graduation.project.bas.domain.eu;

/**
 * @author shanhe
 * @className LandmarkReturnType
 * @date 2020-09-23 16:54
 **/
public enum LandmarkReturnType {
    HOT_LANDMARKS(1),
    POTENTIAL_LANDMARKS(2);

    private int value;

    LandmarkReturnType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static LandmarkReturnType getPotentialLandmarksByValue(int value) {
        if (value == HOT_LANDMARKS.getValue()) {
            return HOT_LANDMARKS;
        } else if (value == POTENTIAL_LANDMARKS.getValue()) {
            return POTENTIAL_LANDMARKS;
        } else {
            return null;
        }
    }
}
