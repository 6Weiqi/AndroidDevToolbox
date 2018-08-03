package com.liuwq.base.util;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述:
 * 作者: su
 * 日期: 2017/11/13 17:06
 */

public class TimeUtil {
    public static final int MILLISECONDS_OF_ONE_SECOND = 1000;  //每秒对应毫秒数
    public static final int MILLISECONDS_OF_ONE_MINUTE = 1000 * 60;  //每分对应毫秒数
    public static final int MILLISECONDS_OF_ONE_HOUR = 1000 * 60 * 60;   //每时对应毫秒数
    public static final int SECONDS_OF_ONE_MINUTE = 60;  //每分钟对应秒数
    public static final int SECONDS_OF_ONE_HOUR = 60 * 60;  //每小时对应秒数
    public static final int MINUTE_OF_ONE_HOUR = 60;  //每小时对应分钟数

    /**
     * 毫秒转秒
     *
     * @param ms
     * @return
     */
    public static long millisecondToSecond(long ms) {
        return ms / MILLISECONDS_OF_ONE_SECOND;
    }

    /**
     * 毫秒转分
     *
     * @param ms
     * @return
     */
    public static long millisecondToMinute(long ms) {
        return ms / MILLISECONDS_OF_ONE_MINUTE;
    }

    /**
     * 毫秒转时
     *
     * @param ms
     * @return
     */
    public static long millisecondToHour(long ms) {
        return ms / MILLISECONDS_OF_ONE_HOUR;
    }

    /**
     * 秒转毫秒
     *
     * @param s
     * @return
     */
    public static long secondToMillisecond(long s) {
        return s * MILLISECONDS_OF_ONE_SECOND;
    }

    /**
     * 分转毫秒
     *
     * @param m
     * @return
     */
    public static long minuteToMillisecond(long m) {
        return m * MILLISECONDS_OF_ONE_MINUTE;
    }

    /**
     * 时转毫秒
     *
     * @param h
     * @return
     */
    public static long hourToMillisecond(long h) {
        return h * MILLISECONDS_OF_ONE_HOUR;
    }

    /**
     * 分转秒
     *
     * @param m
     * @return
     */
    public static long minuteToSecond(long m) {
        return m * SECONDS_OF_ONE_MINUTE;
    }

    /**
     * 小时转秒
     *
     * @param h
     * @return
     */
    public static long hourToSecond(long h) {
        return h * SECONDS_OF_ONE_HOUR;
    }

    /**
     * 小时转分
     *
     * @param h
     * @return
     */
    public static long hourToMinute(long h) {
        return h * MINUTE_OF_ONE_HOUR;
    }

    public static String millisecondToMMSS(long ms) {
        return millisecondToHHMMSS(ms).substring(3);
    }

    /**
     * 毫秒转换为HH:MM:SS格式时间字符串
     *
     * @param ms
     * @return
     */
    public static String millisecondToHHMMSS(long ms) {
        int hour;
        int minute;
        int second;

        hour = (int) (ms / MILLISECONDS_OF_ONE_HOUR);
        minute = (int) ((ms % MILLISECONDS_OF_ONE_HOUR) / MILLISECONDS_OF_ONE_MINUTE);
        second = (int) (((ms % MILLISECONDS_OF_ONE_HOUR) % MILLISECONDS_OF_ONE_MINUTE)
                / MILLISECONDS_OF_ONE_SECOND);

        return toDoubleDigit(hour) + ":" + toDoubleDigit(minute) + ":" + toDoubleDigit(second);
    }

    /**
     * 秒转换为HH:MM:SS格式时间字符串
     *
     * @param s
     * @return
     */
    public static String secondToHHMMSS(long s) {
        return millisecondToHHMMSS(secondToMillisecond(s));
    }

    /**
     * 具体时间转换为毫秒值
     *
     * @param h
     * @param m
     * @param s
     * @return
     */
    public static long timeToMillisecond(int h, int m, int s) {
        return timeToMillisecond(toDoubleDigit(h) + ":" + toDoubleDigit(m) + ":" + toDoubleDigit(s));
    }

    /**
     * 具体时间转换为秒值
     *
     * @param h
     * @param m
     * @param s
     * @return
     */
    public static long timeToSecond(int h, int m, int s) {
        return millisecondToSecond(timeToMillisecond(toDoubleDigit(h) + ":" + toDoubleDigit(m) + ":"
                + toDoubleDigit(s)));
    }

    /**
     * HH:MM:SS格式时间字符串转换为对应毫秒值
     *
     * @param time
     * @return
     */
    public static long timeToMillisecond(String time) {
        if (time == null || time.isEmpty()) {
            return 0;
        }

        String[] timeArr = time.split(":");

        if (timeArr.length != 3) {
            // illegal time string
            return 0;
        }

        int hour = Integer.valueOf(timeArr[0].substring(0, 2));
        int minute = Integer.valueOf(timeArr[1].substring(0, 2));
        int second = Integer.valueOf(timeArr[2].substring(0, 2));

        return (hour * MILLISECONDS_OF_ONE_HOUR) + (minute * MILLISECONDS_OF_ONE_MINUTE)
                + (second * MILLISECONDS_OF_ONE_SECOND);
    }

    /**
     * HH:MM:SS格式时间字符串转换为对应秒值
     *
     * @param time
     * @return
     */
    public static long timeToSecond(String time) {
        return millisecondToSecond(timeToMillisecond(time));
    }

    /**
     * 将数字转换为2位数字符串（不足两位补0，超过2位不改变）
     *
     * @param num
     * @return
     */
    public static String toDoubleDigit(int num) {
        String strNum = String.valueOf(num);

        if (strNum.length() == 1) {
            strNum = "0" + strNum;
        }
        return strNum;
    }

    /**
     * 获取指定位数时间戳
     *
     * @param length
     * @return
     */
    public static String getTimeStamp(int length) {
        return String.valueOf(System.currentTimeMillis()).substring(0, length);
    }

    public static String getCurrentTimeHHMM() {
        return new SimpleDateFormat(FormatUtil.YYYY_MM_DD_HH_MM_SS).format(new Date());
    }

    public static boolean isSameDay(@NonNull Date d1, @NonNull Date d2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))
                && (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
    }


}
