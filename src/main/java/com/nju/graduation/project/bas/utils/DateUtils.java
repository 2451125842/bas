package com.nju.graduation.project.bas.utils;

import com.nju.graduation.project.bas.domain.eu.DurationType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 * @author shanhe
 * @className DateUtils
 * @date 2020-09-23 18:17
 **/
public class DateUtils {

    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_FORMAT);
    private static final int DurationTypeReturnNum = 2;

    public static String getFormatDate(String date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatter);
    }

    public static int getGapOfTwoDate(String start, String end) {
        DateTime starDate = new DateTime(start);
        DateTime endDate = new DateTime(end);
        return endDate.getDayOfYear()-starDate.getDayOfYear();
    }

    public static String[] formateDate(String start, String end) {
        String[] res = new String[DurationTypeReturnNum];
        int gapDays = getGapOfTwoDate(start, end);
        getDate(gapDays, res, new DateTime(end));
        return res;
    }

    private static void getDate(int days, String[] res, DateTime endDate) {
        DateTime startDate = endDate.plusDays(1-days);
        res[0] = startDate.toString(formatter);
        res[1] = endDate.toString(formatter);
    }

    public static String[] getFormatePreDate(String start, String end) {
        String[] res = new String[DurationTypeReturnNum];
        int gapDays = getGapOfTwoDate(start, end);
        getDate(gapDays, res, new DateTime(start).plusDays(-1));
        return res;
    }
}
