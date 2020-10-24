package com.wyf.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: DateUtils
 * @Description: 时间类工具类
 * @Author: wyf
 * @Date: 2020/10/2 20:37
 * @version: 1.0
 */
public class DateUtils {

    // 格式：2007年06月07日 12时12分12秒234毫秒
    private final static String[] FORMAT_CHINA = {"年", "月", "日", "时", "分", "秒", "毫秒"};
    // 格式：2007-06-07 12:12:12 234
    private final static String[] FORMAT_NORMAL = {"-", "-", "", ":", ":", "", ""};
    // 格式：2007/06/07 12:12:12 234
    private final static String[] FORMAT_DATATIME = {"/", "/", "", ":", ":", "", ""};
    // 格式：中文星期
    private final static String[] FORMAT_WEEK = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};


    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat dateFormat8 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateFormat9 = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat dateFormat_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat_3 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormat_4 = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat dateFormat_5 = new SimpleDateFormat("dd/MM");
    private static SimpleDateFormat dateFormat_6 = new SimpleDateFormat("yyyy年MM月dd日");


    /**
     * @Description 返回指定日期格式时间
     * @ClassName   getTodayYear
     * @Date        8:29 2020/10/3
     * @Param       [date,format]
     * @return      java.lang.String
     */
    public static String getDateFormat(Date date,String format) {
        if (date == null || StringUtils.isBlank(format)){
            return null;
        }

        return DateFormatUtils.format(date, format);
    }

    /**
     * 获取今日年份
     * @return yyyy
     */
    public static String getTodayYear() {
        return DateFormatUtils.format(new Date(), "yyyy");
    }

    /**
     * 获取今日月份
     * @return MM
     */
    public static String getTodayMonth() {
        return DateFormatUtils.format(new Date(), "MM");
    }

    /**
     * 获取今日日期
     * @return dd
     */
    public static String getTodayDay() {
        return DateFormatUtils.format(new Date(), "dd");
    }

    /**
     * 获取短日月
     * @return MMdd
     */
    public static String getTodayChar4() {
        return DateFormatUtils.format(new Date(), "MMdd");
    }

    /**
     * 把日期转换成6字符的格式类型字符串，如200801
     * @param date 日期
     * @return String
     */
    public static String getChar8(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }

    /**
     * 返回年月
     * @return yyyyMM
     */
    public static String getTodayChar6() {
        return DateFormatUtils.format(new Date(), "yyyyMM");
    }

    /**
     * 返回年月日
     * @return yyyyMMdd
     */
    public static String getTodayChar8() {
        return DateFormatUtils.format(new Date(), "yyyyMMdd");
    }

    /**
     * 返回 年月日小时分
     * @return yyyyMMddHHmm
     */
    public static String getTodayChar12() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
    }

    /**
     * 返回 年月日小时分秒
     * @return yyyyMMddHHmmss
     */
    public static String getTodayChar14() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 返回 年月日小时分秒 毫秒
     * @return yyyyMMddHHmmssS
     */
    public static String getTodayChar17() {
        String dateString = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS");
        int length = dateString.length();

        if (length < 17) {
            String endStr = dateString.substring(14, length);
            int len = endStr.length();
            for (int i = 0; i < 3 - len; i++) {
                endStr = "0" + endStr;
            }
            dateString = dateString.substring(0, 14) + endStr;
        }
        return dateString;
    }

    /**
     * 将指定Date类型参数转换为指定的Oracle日期时间格式字符串
     * @return String
     */
    public static String getOracleDate(Date inputDateTime) throws NullPointerException {
        if (null == inputDateTime) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(inputDateTime);
    }


    /**
     * 比对两个时间间隔
     * @param startDateTime 开始时间
     * @param endDateTime 结束时间
     * @param distanceType 计算间隔类型 天d 小时h 分钟m 秒s
     * @return
     */
    public static String getDistanceDT(String startDateTime, String endDateTime, String distanceType) {
        String strResult = "";
        long lngDistancVal = 0;
        try {
            SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date startDate = tempDateFormat.parse(startDateTime);
            Date endDate = tempDateFormat.parse(endDateTime);
            if ( distanceType.equals("d") ) {
                lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
            }
            else if ( distanceType.equals("h") ) {
                lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
            }
            else if ( distanceType.equals("m") ) {
                lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000 * 60);
            }
            else if ( distanceType.equals("s") ) {
                lngDistancVal = (endDate.getTime() - startDate.getTime()) / (1000);
            }
            strResult = String.valueOf(lngDistancVal);
        }
        catch (Exception e) {
            strResult = "0";
        }
        return strResult;
    }

    /**
     * @Description 获取毫秒数
     * @ClassName   currentTimeMillis
     * @Date        8:20 2020/10/3
     * @Param       [strDate]
     * @return      long
     */
    public static long currentTimeMillis(String strDate) {
        long timeMillis = 0;
        if ( strDate == null || strDate.trim().length() != 10 ) { return timeMillis; }
        String year = strDate.trim().substring(0, 4);
        String month = strDate.trim().substring(5, 7);
        String day = strDate.trim().substring(8, 10);
        timeMillis = Long.parseLong(year + month + day + "000000");
        return timeMillis;
    }


    /**
     * 判断终止日期是否大于开始日期
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static boolean getDistanceDate(String startDateTime, String endDateTime) {
        boolean blnResult = false;
        long lngDistancVal = 0;
        try {
            SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date startDate = tempDateFormat.parse(startDateTime);
            Date endDate = tempDateFormat.parse(endDateTime);
            lngDistancVal = endDate.getTime() - startDate.getTime();
            blnResult = lngDistancVal > 0 ? true : false;
        }
        catch (Exception e) {
            blnResult = false;
            e.printStackTrace();
        }
        return blnResult;
    }

    /**
     * 判断终止日期是否大于开始日期
     * @param startDateTime
     * @param endDateTime
     * @return
     */
    public static boolean getDistanceDate(Date startDateTime, Date endDateTime) {
        boolean blnResult = false;
        long lngDistancVal = 0;
        try {
            lngDistancVal = startDateTime.getTime() - endDateTime.getTime();
            blnResult = lngDistancVal > 0 ? true : false;
        }
        catch (Exception e) {
            blnResult = false;
            e.printStackTrace();
        }
        return blnResult;
    }



    /**
     * 获取某个日期若干天后的时间
     *
     * @param days
     * @return
     */
    public static String getStartDateNextTime(String startDateTime, int days) {
        try {
            SimpleDateFormat tempDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date startDate = tempDateFormat.parse(startDateTime);
            Calendar theCa = Calendar.getInstance();
            theCa.setTime(startDate);
            theCa.add(Calendar.DATE, days);
            return DateFormatUtils.format(theCa.getTime(), "yyyyMMddHHmmss");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前时间若干天后的时间
     *
     * @param days
     * @return
     */
    public static String getNextTime(int days) {
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(new Date());
        theCa.add(Calendar.DATE, days);
        return DateFormatUtils.format(theCa.getTime(), "yyyyMMddHHmmss");
    }


    /*
     * 获取当天的上一个月
     */
    public static String getBeforeMonthDay(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
    }
    /*
     * 获取当天的上一个月
     */
    public static String getBeforeMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("yyyyMM").format(cal.getTime());
    }

    /*
     * 获取当天的上一个月第一天
     */
    public static String getBeforeMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        String date = DateUtils.getTodayChar8();
        calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6))-2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }


    /**
     * 查询前一天的最近6个月月份信息
     *
     * @return
     */
    public static String[] getQueryMonth() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String[] query_ym = new String[5];

        String yearInfo = String.valueOf(cal.get(Calendar.YEAR));
        int monthTemp = cal.get(Calendar.MONTH) + 1;
        String monthInfo = String.valueOf(monthTemp);
        if (monthTemp < 10) {
            monthInfo = "0" + monthInfo;
        }
        query_ym[4] = yearInfo + monthInfo;

        for (int i = 4; i >= 0; i--) {
            cal.add(Calendar.MONTH, -1);
            int month = cal.get(Calendar.MONTH) + 1;
            String monthStr = String.valueOf(month);
            if (month < 10) {
                monthStr = "0" + monthStr;
            }

            query_ym[i] = String.valueOf(cal.get(Calendar.YEAR)) + monthStr;
        }
        return query_ym;
    }

    /**
     * 获取当月最后几天
     * @param days   最后几天
     * @return
     */
    public static List<String> getLastDays(int days) {
        Calendar calendar = Calendar.getInstance();
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= days; i++) {
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DATE, 1);
            calendar.add(Calendar.DATE, -i);
            Date theDate = calendar.getTime();
            String s = dateFormat8.format(theDate);
            list.add(s);
        }
        return list;
    }



}
