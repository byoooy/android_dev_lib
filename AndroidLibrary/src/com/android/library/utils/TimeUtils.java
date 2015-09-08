package com.android.library.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式转换工具
 **/
@SuppressLint("SimpleDateFormat")
public class TimeUtils {
	/**
	 * 年:yyyy 月:MM 日:dd 时:HH 分:mm 秒:ss
	 */

	private static TimeUtils INSTANCE;

	public static TimeUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TimeUtils();
		}
		return INSTANCE;
	}

	/** 获取当前日期，格式为：2015-08-23 */
	public String getCurrentDay() {
		return getCurrentTime("yyyy-MM-dd");
	}

	/** 获取当前日期，格式为：2015/08/23 12:01 */
	public String getCurrentTime() {
		return getCurrentTime("yyyy/MM/dd HH:mm");
	}

	/** 获取当前日期，返回日期格式自定义 */
	public String getCurrentTime(String patten) {
		SimpleDateFormat format = new SimpleDateFormat(patten);
		Long time = new Long(new Date().getTime());
		String d = format.format(time);
		return d;
	}

	/** 将传入的时间戳转化为指定日期格式，格式为：2015/08/23 */
	public String getStrTimeFromLong(long time) {
		return getStrTimeFromLong(time, "yyyy/MM/dd");
	}

	/** 将传入的时间戳转化为指定日期格式，格式自定义 */
	public String getStrTimeFromLong(long time, String patten) {
		SimpleDateFormat format = new SimpleDateFormat(patten);
		String d = format.format(time);
		return d;
	}

	/** 日期格式转化，将制定格式的日期转化为另一种日期格式 */
	public String getCorrectTime(String strTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Long time = (long) 0;
		try {
			time = format.parse(strTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
		String d = format2.format(time);
		return d;
	}

	/** 将指定格式的日期转化为时间戳，格式为2015/08/23 12:02 */
	public long getLongTime(String strTime) {
		return getLongTime(strTime, "yyyy/MM/dd HH:mm");
	}

	/** 将指定格式的日期转化为时间戳,格式为：12:01 */
	public long getLongTimeHHmm(String strTime) {
		return getLongTime(strTime, "HH:mm");
	}

	/** 将指定格式的日期转化为时间戳,格式自定义 */
	public long getLongTime(String strTime, String patten) {
		SimpleDateFormat format = new SimpleDateFormat(patten);
		long time = 0;
		try {
			time = format.parse(strTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	public int countDays(long oldTime) {
		long time = new Date().getTime();
		long objectTime = time - oldTime;
		int days = (int) (objectTime / (3600 * 1000 * 24));
		return days;
	}

	public String getStrTimeFromYMD(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String d = format.format(c.getTime());
		return d;
	}

}
