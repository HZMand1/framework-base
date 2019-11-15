package com.base.utils.type;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO 时间工具类
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2019/11/12 16:24
 * @copyright XXX Copyright (c) 2019
 */
public class DateUtil {
    public static final String yyyy_MM_dd_EN = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_decline = "yyyy/MM/dd";
    public static final String yyyyMMdd_EN = "yyyyMMdd";
    public static final String yyyy_MM_EN = "yyyy-MM";
    public static final String yyyyMM_EN = "yyyyMM";
    public static final String yyyy_MM_dd_HH_mm_ss_EN = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_HH_mm_ss_S_EN = "yyyy-MM-dd HH:mm:ss.S";
    public static final String yyyyMMddHHmmss_EN = "yyyyMMddHHmmss";
    public static final String yyyy_MM_dd_CN = "yyyy年MM月dd日";
    public static final String yyyy_MM_dd_HH_mm_ss_CN = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String yyyy_MM_dd_HH_mm_CN = "yyyy年MM月dd日HH时mm分";
    public static final String BJBOSS_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String HH_mm_ss_EN = "HH:mm:ss";
    private static Map<String, DateFormat> dateFormatMap = new HashMap();

    public DateUtil() {
    }

    public static DateFormat getDateFormat(String formatStr) {
        DateFormat df = (DateFormat)dateFormatMap.get(formatStr);
        if (df == null) {
            df = new SimpleDateFormat(formatStr);
            dateFormatMap.put(formatStr, df);
        }

        return (DateFormat)df;
    }

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date getDate(String dateTimeStr, String formatStr) {
        try {
            if (dateTimeStr != null && !dateTimeStr.equals("")) {
                DateFormat sdf = getDateFormat(formatStr);
                return sdf.parse(dateTimeStr);
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static Date string2Date(String strDate, String pattern) {
        if (strDate != null && !strDate.equals("")) {
            if (pattern == null || pattern.equals("")) {
                pattern = "yyyy-MM-dd";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = null;

            try {
                date = sdf.parse(strDate);
                return date;
            } catch (ParseException var5) {
                throw new RuntimeException(var5);
            }
        } else {
            throw new RuntimeException("str date null");
        }
    }

    public static Date convertDate(String dateTimeStr) {
        try {
            if (dateTimeStr != null && !dateTimeStr.equals("")) {
                DateFormat sdf = getDateFormat("yyyy-MM-dd");
                Date d = sdf.parse(dateTimeStr);
                return d;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static Date getDate(String dateTimeStr) {
        return getDate(dateTimeStr, "yyyy-MM-dd");
    }

    public static Date transferDate(String date) throws Exception {
        if (date != null && date.length() >= 1) {
            if (date.length() != 8) {
                throw new Exception("日期格式错误");
            } else {
                String con = "-";
                String yyyy = date.substring(0, 4);
                String mm = date.substring(4, 6);
                String dd = date.substring(6, 8);
                int month = Integer.parseInt(mm);
                int day = Integer.parseInt(dd);
                if (month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                    String str = yyyy + con + mm + con + dd;
                    return getDate(str, "yyyy-MM-dd");
                } else {
                    throw new Exception("日期格式错误");
                }
            }
        } else {
            return null;
        }
    }

    public static String dateToDateString(Date date) {
        return dateToDateString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToDateFullString(Date date) {
        return null == date ? null : dateToDateString(date, "yyyyMMddHHmmss");
    }

    public static String dateToDateString(Date date, String formatStr) {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    public static String stringToDateString(String date, String formatStr1, String formatStr2) {
        Date d = getDate(date, formatStr1);
        DateFormat df = getDateFormat(formatStr2);
        return df.format(d);
    }

    public static String getCurDate() {
        return dateToDateString(new Date(), "yyyy-MM-dd");
    }

    public static String getCurDate(String formatStr) {
        return dateToDateString(new Date(), formatStr);
    }

    public static String getCurCNDate() {
        return dateToDateString(new Date(), "yyyy年MM月dd日");
    }

    public static String getCurDateTime() {
        return dateToDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), "yyyy年MM月dd日HH时mm分ss秒");
    }

    public static long compareDateStr(String time1, String time2) {
        Date d1 = getDate(time1);
        Date d2 = getDate(time2);
        return d2.getTime() - d1.getTime();
    }

    public static long compareDateStr(String time1, String time2, String format) {
        Date d1 = getDate(time1, format);
        Date d2 = getDate(time2, format);
        return d2.getTime() - d1.getTime();
    }

    public static long compareDateNow(String time, String format) {
        Date date = getDate(time, format);
        return (new Date()).getTime() - date.getTime();
    }

    public static long compareDateStr(Date time1, Date time2) {
        return time2.getTime() - time1.getTime();
    }

    public static boolean isTimeBefor(Date nows, Date date) {
        long hous = nows.getTime() - date.getTime();
        return hous > 0L;
    }

    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd = hours.multiply(new BigDecimal(3600000));
        return bd.longValue();
    }

    public static String getDateStringOfYear(int years, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(1, years);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static String getDateStringOfMon(int months, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(2, months);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static String getDateStringOfDay(int days, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(5, days);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static int theDateIsToday(String date, String format) {
        String theDate = stringToDateString(date, format, "yyyyMMdd");
        String today = getDateStringOfDay(0, "yyyyMMdd");
        return theDate.equals(today) ? 1 : 0;
    }

    public static String getDateStringOfHour(int hours, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(11, hours);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static String getDateOfMon(String date, int mon, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(getDate(date, formatStr));
        now.add(2, mon);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static String getDateOfDay(String date, int day, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(getDate(date, formatStr));
        now.add(5, day);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static Date getDate(Date beginDate, int ds) {
        if (ds == 0) {
            return new Date();
        } else {
            try {
                SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
                Calendar date = Calendar.getInstance();
                date.setTime(beginDate);
                date.set(5, date.get(5) - ds);
                Date endDate = dft.parse(dft.format(date.getTime()));
                return endDate;
            } catch (ParseException var5) {
                var5.printStackTrace();
                return new Date();
            }
        }
    }

    public static String getAfterNDays(Date date, int n, String formateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formateStr);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(5, n);
        return sdf.format(calendar.getTime());
    }

    public static String getDateOfMin(String date, int mins, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(getDate(date, formatStr));
        now.add(13, mins * 60);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static Date getDateOfMin(Date date, int mins) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(date);
        now.add(13, mins * 60);
        return now.getTime();
    }

    public static String getDateStringOfMin(int mins, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(12, mins);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static Date getDateOfMin(int mins) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(12, mins);
        return now.getTime();
    }

    public static String getDateStringOfSec(int sec, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(13, sec);
        return dateToDateString(now.getTime(), formatStr);
    }

    public static int getMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(5);
    }

    public static int getCurentMonthDay() {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    public static int getMonthDay(String date) {
        Date strDate = getDate(date, "yyyy-MM-dd");
        return getMonthDay(strDate);
    }

    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(1);
    }

    public static int getMonth(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(2) + 1;
    }

    public static int getDay(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(5);
    }

    public static int getHour(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(11);
    }

    public static int getMin(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(12);
    }

    public static int getSecond(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(13);
    }

    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(7) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }

        c.add(5, -day_of_week + 1);
        return dateToDateString(c.getTime(), "yyyy-MM-dd");
    }

    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(7) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }

        c.add(5, -day_of_week + 7);
        return dateToDateString(c.getTime());
    }

    public static String getDayOfThisWeek(int num) {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(7) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }

        c.add(5, -day_of_week + num);
        return dateToDateString(c.getTime(), "yyyy-MM-dd");
    }

    public static String getDayOfThisMoon(String num) {
        String date = dateToDateString(new Date(), "yyyy-MM");
        date = date + "-" + num;
        return date;
    }

    public static long getQuotByDays(String beginDate, String endDate) {
        long quot = 0L;
        DateFormat df = getDateFormat("yyyy-MM-dd");

        try {
            Date d1 = df.parse(beginDate);
            Date d2 = df.parse(endDate);
            quot = d2.getTime() - d1.getTime();
            quot = quot / 1000L / 60L / 60L / 24L;
            return quot;
        } catch (ParseException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static String getDateAddDay(String date, int days, String format) {
        DateFormat df = getDateFormat(format);

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(df.parse(date));
            cal.set(6, cal.get(6) + days);
            date = df.format(cal.getTime());
            return date;
        } catch (ParseException var5) {
            throw new RuntimeException(var5);
        }
    }

    public static Date getLastDayOfCurrMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(2, 1);
        cal.set(5, 0);
        return cal.getTime();
    }

    public static String getDateAddMonth(String date, int m) {
        DateFormat df = getDateFormat("yyyyMM");

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(df.parse(date));
            cal.add(2, m);
            date = df.format(cal.getTime());
            return date;
        } catch (ParseException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        int lastDay = cal.getActualMinimum(5);
        cal.set(5, lastDay);
        DateFormat df = getDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        int lastDay = cal.getActualMaximum(5);
        cal.set(5, lastDay);
        DateFormat df = getDateFormat("yyyy-MM-dd");
        return df.format(cal.getTime());
    }

    public static String getYesterday(Date date) throws ParseException {
        DateFormat df = getDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(df.format(date)));
        calendar.add(5, -1);
        return df.format(calendar.getTime());
    }

    public static String getIntToStr(String dateInt, String format) {
        DateFormat df = getDateFormat(format);
        long times = (long)Integer.parseInt(dateInt) * 1000L;
        Date date = new Date(times);
        return df.format(date);
    }

    public static Integer getDateInt() {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    public static String getLongToStr(long time, String format) {
        Date date = new Date(time);
        return dateToDateString(date, format);
    }

    public static int getIntervalSec(int start, int end) {
        return (end - start) * 60 * 60;
    }

    public static String getMillsStr(long time) {
        String timeStr = String.valueOf(time);
        String suffix = timeStr.substring(0, timeStr.length() - 3);
        String prefix = timeStr.substring(timeStr.length() - 3, timeStr.length());
        return suffix + "." + prefix;
    }

    public static String longToString(String timeStr, String formatStr) {
        long times = Long.parseLong(timeStr.replace(".", ""));
        Date date = new Date(times);
        return dateToDateString(date, formatStr);
    }

    public static Long getTodayTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(11, 0);
        todayStart.set(12, 0);
        todayStart.set(13, 0);
        todayStart.set(14, 0);
        return todayStart.getTime().getTime();
    }

    public static Integer getTodayInt() {
        return (int)(getTodayTime() / 1000L);
    }

    public static Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(10, 23);
        todayEnd.set(12, 59);
        todayEnd.set(13, 59);
        todayEnd.set(14, 999);
        return todayEnd.getTime().getTime();
    }

    public static Integer getTomorrowInt() {
        return (int)(getTomorrowTime() / 1000L);
    }

    public static Long getTomorrowTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        cal.add(5, 1);
        return cal.getTime().getTime();
    }

    public static Long getPointHourTime(int hour) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(11, hour);
        todayStart.set(12, 0);
        todayStart.set(13, 0);
        todayStart.set(14, 0);
        return todayStart.getTime().getTime();
    }

    public static Long getPointDateHourTime(int days, int hour) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.add(5, days);
        todayStart.set(11, hour);
        todayStart.set(12, 0);
        todayStart.set(13, 0);
        todayStart.set(14, 0);
        return todayStart.getTime().getTime();
    }

    public static Integer hourTosec(String time) {
        if (!"null".equals(time) && !StringUtil.isEmpty(time)) {
            if (time.length() <= 5) {
                time = time + ":00";
            }

            int index1 = time.indexOf(":");
            int index2 = time.indexOf(":", index1 + 1);
            int hh = Integer.parseInt(time.substring(0, index1));
            int mi = Integer.parseInt(time.substring(index1 + 1, index2));
            int ss = Integer.parseInt(time.substring(index2 + 1));
            return hh * 60 * 60 + mi * 60 + ss;
        } else {
            return null;
        }
    }

    public static Integer minTosec(String time) {
        if (time.length() <= 5) {
            time = time + ":00";
        }

        int index1 = time.indexOf(":");
        int index2 = time.indexOf(":", index1 + 1);
        int mi = Integer.parseInt(time.substring(0, index1));
        int ss = Integer.parseInt(time.substring(index1 + 1, index2));
        return mi * 60 + ss;
    }

    public static void main(String[] args) {
        System.out.println(isInDate(getDate("2016-08-01 09:02:00", "yyyy-MM-dd HH:mm:ss"), "09:00:00", "23:00:00"));
        System.out.println(isInDate("2016-08-01 09:02:00", "09:00:00", "23:00:00"));
    }

    public static boolean isDate(String dateTimeStr, String formatStr) {
        DateFormat df = getDateFormat(formatStr);

        try {
            df.parse(dateTimeStr);
            return true;
        } catch (Exception var4) {
            return false;
        }
    }

    public static boolean isInDate(String strDate, String strDateBegin, String strDateEnd) {
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if (strDateH >= strDateBeginH && strDateH <= strDateEndH) {
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {
                return true;
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM <= strDateEndM) {
                return true;
            } else {
                return strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM && strDateS <= strDateEndS;
            }
        } else {
            return false;
        }
    }

    public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if (strDateH >= strDateBeginH && strDateH <= strDateEndH) {
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {
                return true;
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM <= strDateEndM) {
                return true;
            } else {
                return strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM && strDateS <= strDateEndS;
            }
        } else {
            return false;
        }
    }

    public static boolean isInDate(Date date, Date dateBegin, Date dateEnd) {
        boolean flag = false;
        Long curtDate = date.getTime();
        Long beginDate = dateBegin.getTime();
        Long endDate = dateEnd.getTime();
        if (curtDate > beginDate && curtDate < endDate) {
            flag = true;
        } else if (curtDate == beginDate || curtDate == endDate) {
            flag = true;
        }

        return flag;
    }

    public static boolean isInTime(int time, int begin, int end) {
        return time >= begin && time < end;
    }

    public static int getMinutest(String begin, String format) {
        String nowMinutes = getCurDate("HH:mm");
        long time = compareDateStr("09:00", nowMinutes, "HH:mm");
        return (int)time;
    }

    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(6, calendar.get(6) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    public static List<String> getPastDayList(int intervals) {
        List<String> pastDaysList = new ArrayList();

        for(int i = 0; i < intervals; ++i) {
            pastDaysList.add(getPastDate(i));
        }

        return pastDaysList;
    }
}
