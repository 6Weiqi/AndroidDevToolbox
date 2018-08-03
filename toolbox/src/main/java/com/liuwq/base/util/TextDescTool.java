package com.liuwq.base.util;

import android.net.ParseException;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TextDescTool {

    public static String getDate(long curTime) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(curTime);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        StringBuffer buffer = new StringBuffer();
        buffer.append(year).append("年").append(month + 1).append("月")
                .append(day).append("日");

        return buffer.toString();
    }

    /**
     * 保留一位小数点
     *
     * @param f
     * @return 作者:fighter <br />
     * 创建时间:2013-6-13<br />
     * 修改时间:<br />
     */
    public static String floatMac1(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("####.#");
        try {
            return decimalFormat.format(f);
        } catch (Exception e) {
            return f + "";
        }
    }

    public static String floatMac(String floatStr) {
        DecimalFormat decimalFormat = new DecimalFormat("####.#");
        try {
            float f = Float.parseFloat(floatStr);
            return decimalFormat.format(f);
        } catch (Exception e) {
            return floatStr;
        }
    }

    /**
     * 获取几天以前的秒数
     *
     * @param day
     * @return 作者:fighter <br />
     * 创建时间:2013-6-7<br />
     * 修改时间:<br />
     */
    public static String dayBefore(float day) {
        // Calendar calendar = Calendar.getInstance();
        // calendar.add(Calendar.DAY_OF_WEEK, 0 - day);
        // return (calendar.getTimeInMillis() / 1000) + "";
        long time = (long) (60 * 60 * 24 * day);

        return time + "";

    }

    public static Calendar getCalendar(String birthDate) {
        Calendar calendar = Calendar.getInstance();
        if (TextUtils.isEmpty(birthDate)) {
            return calendar;
        }
        String birth = birthDate;
        try {
            Date date = new Date(birth); // 出生日期d1
            calendar.setTime(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static String getTime(String curTime) {
        long time;
        try {
            time = Long.parseLong(curTime);
        } catch (Exception e) {
            time = System.currentTimeMillis();
        }

        return getTime(time);
    }

    public static String getTime(long curTime) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(curTime);
        String str = timeDifference(calendar);
        Date date = calendar.getTime();
        SimpleDateFormat format = null;
        if (str.endsWith("刚刚") || str.endsWith("分钟前") || str.endsWith("小时前")) {
            format = new SimpleDateFormat("HH:mm:ss");
        } else if (str.endsWith("天前") || str.endsWith("很久以前")) {
            format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }

        return format.format(date);
    }

    /**
     * 出生日期转换为年龄
     *
     * @param birthDate
     * @return 作者:fighter <br />
     * 创建时间:2013-3-26<br />
     * 修改时间:<br />
     */
    public static int dateToAge(String birthDate) {
        if (TextUtils.isEmpty(birthDate)) {
            return 0;
        }
        int age = 0;
        try {
            Calendar cal = Calendar.getInstance();
            String birth = birthDate;
            String now = (cal.get(Calendar.YEAR) + "/"
                    + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DATE));
            Date d1 = new Date(birth); // 出生日期d1
            Date d2 = new Date(now); // 当前日期d2
            long i = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
            int g = (int) i;
            age = g / 365;
            // age = age > 0 ? age : 1;
        } catch (IllegalArgumentException e) {
        }

        return age;
    }

    public static int dateToAge1(String birthDate) {
        if (TextUtils.isEmpty(birthDate)) {
            return 0;
        }
        int age = 0;
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String now = (cal.get(Calendar.YEAR) + "/"
                    + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DATE));
            Date d1 = format.parse(birthDate);
            Date d2 = new Date(now); // 当前日期d2
            long i = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
            int g = (int) i;
            age = g / 365 + 1;
            // age = age > 0 ? age : 1;
        } catch (IllegalArgumentException e) {
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return age;
    }


    public static String timeDifference(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        return timeDifference(calendar);
    }

    private static String secondOnline(long second, int index, int num) {
        String info;
        if (index == 60) {
            info = "分钟";
        } else if (index == (60 * 60)) {
            info = "小时";
        } else if (index == (24 * 60 * 60)) {
            info = "天";
        } else {
            return "30天以及更久";
        }

        if (second < index * num) {
            return num + info;
        } else {
            return secondOnline(second, index, ++num);
        }
    }

    public static String timeDifference1(String currTime) {
        if (!TextUtils.isEmpty(currTime)) {
            Date curDate = null;
            try {
                try {
                    curDate = new SimpleDateFormat("yyyyMMddHHmmss")
                            .parse(currTime);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            long longTime = curDate.getTime();
            try {
                calendar.setTimeInMillis(longTime);
            } catch (Exception e) {
            }
            return timeDifference(calendar);

        }
        return "";

    }

    public static String timeDifference(String currTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            long curr = Long.parseLong(currTime);
            calendar.setTimeInMillis(curr);
        } catch (Exception e) {
        }

        return timeDifference(calendar);
    }

    /**
     * 判断时间与当前时间的差距， 给予字符提示.
     *
     * @param calendar
     * @return 作者:fighter <br />
     * 创建时间:2013-4-9<br />
     * 修改时间:<br />
     */
    public static String timeDifference(Calendar calendar) {
        String info;
        Calendar currCalendar = Calendar.getInstance();
        final String isSame = isSameDay(calendar, currCalendar);
        long second = (currCalendar.getTimeInMillis() - calendar
                .getTimeInMillis()) / 1000;
        int index = 0;
        if (second < (60 * 60)) {
            index = 60;
        } else if (second < (24 * 60 * 60)) {
            index = 60 * 60;
        } else if (second < (30 * (24 * 60 * 60))) {
            index = (24 * 60 * 60);
        }
        info = second(second, index, 1);
        if ((info.contains("分钟前") || info.contains("小时前"))
                && "很久以前".equals(isSame))
            return "1天前";
        return info;
    }

    public static String isSameDay(Calendar calendar, Calendar curCalendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String date1 = format.format(calendar.getTime());
        final String date2 = format.format(curCalendar.getTime());
        if (!date1.equals(date2))
            return "很久以前";
        return "";
    }

    private static String second(long second, int index, int num) {
        String info;
        if (index == 60) {
            info = "分钟前";
        } else if (index == (60 * 60)) {
            info = "小时前";
        } else if (index == (24 * 60 * 60)) {
            info = "天前";
        } else {
            return "很久以前";
        }

        if (second < index * num) {
            if (second < 60)
                return "刚刚";
            return num + info;
        } else {
            return second(second, index, ++num);
        }
    }
}
