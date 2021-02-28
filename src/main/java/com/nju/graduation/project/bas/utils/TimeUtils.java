package com.nju.graduation.project.bas.utils;

import java.util.Date;

/**
 * @author shanhe
 * @className TimeUtils
 * @date 2021-02-27 10:36
 **/
public class TimeUtils {

    private static final int UnixTurnTimes = 1000;

    /**
     * unix时间戳
     * @return
     */
    public static long getCurrentUnixTime() {
        return System.currentTimeMillis()/UnixTurnTimes;
    }

    public static Date Unix2Date(long startTime) {
        return new Date(startTime*UnixTurnTimes);
    }
}
