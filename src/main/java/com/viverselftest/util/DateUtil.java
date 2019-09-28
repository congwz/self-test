package com.viverselftest.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Add by jianhan on 2018/3/6
 */
public class DateUtil {

    public static final String DATE = "yyyy-MM-dd";
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIMESTRING = "yyyyMMddHHmmss";
    
    //检测格式yyyy-MM-dd yyyy-MM  yyyy
    public static String getFormat(String date, String pattern) {
    	DateFormat df = new SimpleDateFormat(pattern);
    	Date d = null;
		try {
			d = df.parse(date);
			return df.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    
    }
    
    public static String formatDate(Date date) {
        return formatDateTime(date, DATE);
    }

    public static String formatDateTime(Date date) {
        return formatDateTime(date, null);
    }

    public static String formatDateTime(Date date, String pattern) {
        if (date == null)
            return "";

        if (pattern == null) {
            // default pattern will be used.
            pattern = DATETIME;
        }

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static String formatDateTimeString(Date date) {
        if (date == null)
            return "";

//        if (pattern == null) {
        String pattern = DATETIMESTRING;
//        }

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    
    public static Date formateStringToTime(String date) {
    	return formateStringToTime(date, null);
    }
    
    public static Date formateStringToTime(String date, String pattern) {
    	  if (date == null || "".equals(date) || " ".equals(date))
              return null;

          if (pattern == null) {
              // default pattern will be used.
              pattern = DATE;
          }

          DateFormat df = new SimpleDateFormat(pattern);
          try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    /**
     * JDE使用的是Julian日期,其格式是6位的整数.
     * 第一位是世纪,1代表21世纪,0代表20世纪;
     * 第二和第三位是年，比如1997年是97;
     * 剩下三位是一年中的第几天;
     * 比如2007年的第123天的6位日期数是107123.
     * @param date java Date
     * @return JuLian Date
     */
    public static int dateToJuLian(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR) - 1900;
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        return year * 1000 + dayOfYear;
    }

    /**
     * JDE使用的是Julian日期,其格式是6位的整数.
     * 第一位是世纪,1代表21世纪,0代表20世纪;
     * 第二和第三位是年，比如1997年是97;
     * 剩下三位是一年中的第几天;
     * 比如2007年的第123天的6位日期数是107123.
     * @param date JuLian Date
     * @return java Date
     */
    public static Date juLianToDate(int date) {
        int year = (date / 1000) + 1900;
        int dayOfYear = date % 1000;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        return calendar.getTime();
    }
}
