package com.liuwq.common.base.util;

import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class FormatUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";


    public static String longToDate(String time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String tempTime = String.format("%-13s", time);
        Date dt = new Date(Long.parseLong(tempTime.replace(" ", "0")));
        return format.format(dt);
    }

    public static String longToDate(long time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date dt = new Date(time);
        return format.format(dt);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static Date strToDateLong(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = formatter.parse(dateStr, pos);
        return strToDate;
    }

    public static String getDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }

    public static String getDate2(String pattern1, String pattern2,
                                  String time) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm",
        // Locale.ENGLISH);

//		SimpleDateFormat format = new SimpleDateFormat(pattern1, Locale.CHINA);
        // Date d = format.parse(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern2);
        return format.format(Long.parseLong(time));
    }

    public static long getDate(String pattern1, String time) {
        SimpleDateFormat format = new SimpleDateFormat(pattern1);
        try {
            Date d = format.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }
    }

    public static String longToDate2(String time, String pattern) {

        try {
            long longtime = Long.parseLong(time);
            Date curDate = new Date(longtime);
            String curDateString = new SimpleDateFormat(YYYY_MM_DD)
                    .format(curDate);
            return curDateString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long dateToLong(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date dt = null;
        try {
            dt = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt.getTime();
    }

    /**
     * 小数 {@code d} 保留 {@code digits} 位有效数字
     *
     * @param digits
     * @param d
     * @return
     */
    public static String keepFractionDigits(int digits, double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(digits);
        nf.setMinimumFractionDigits(digits);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(false);
        return nf.format(d);
    }

    /**
     * 将 {@code date} 转换为指定 {@code format}
     *
     * @param date
     * @param format 常用格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String transform(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * {@code src} 的格式是否为 {@code format}
     *
     * @param src
     * @param format
     * @return
     */
    public static boolean isFormatRight(String src, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(src);
            return src.equals(formatter.format(date));
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 将原格式为 {@code srcFormat} 的 {@code dateStr} 转换为指定 {@code desFormat} 返回
     *
     * @param dateStr
     * @param srcFormat
     * @param desFormat
     * @return
     */
    public static String transform(String dateStr, String srcFormat,
                                   String desFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
        try {
            Date date = sdf.parse(dateStr);
            sdf = new SimpleDateFormat(desFormat);
            return sdf.format(date);
        } catch (ParseException e) {
            return dateStr;
        }
    }

    /***
     * 获取 url 指定 name 的 value;
     *
     * @param url
     * @param name
     * @return
     */
    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
            }
        }
        return result;
    }

    public static String getDateFormatStr(@NonNull String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            Date date = formatter.parse(dateStr);
            SimpleDateFormat formatter1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
            String dateString = formatter1.format(date);
            return dateString;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 2000 -> 00分02秒
     *
     * @param milliSeconds
     * @return
     */
    public static String formatMilliseconds(long milliSeconds) {
        if (milliSeconds <= 0 || milliSeconds >= 24 * TimeUtil.MILLISECONDS_OF_ONE_HOUR) {
            return "00分00秒";
        }

        int totalSeconds = (int) TimeUtil.millisecondToSecond(milliSeconds);
        int seconds = totalSeconds % 60;
        int minutes = (int) (TimeUtil.millisecondToMinute(milliSeconds) % 60);

        int hours = (int) TimeUtil.millisecondToHour(milliSeconds);
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d时%02d分%02d秒", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d分%02d秒", minutes, seconds).toString();
        }
    }

    /**
     * 格式化单位
     *
     * @param size  in byte
     * @param scale {@code scale} 位有效数字
     * @return
     */
    public static String getFormatSize(long size, int scale) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + " B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + " K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + " M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + " G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + " T";
    }
}
