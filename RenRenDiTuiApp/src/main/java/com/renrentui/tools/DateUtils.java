package com.renrentui.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 日期帮助类
 * 
 * @author back
 * @version landingtech_v1
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {
	public static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 获取系统当前时间
	 */
	public static String getNowTime() {
		return ft.format(new Date());
	}

	/**
	 * 获取系统当前时间
	 */
	public static String getTime(String date) {
		if(date == null)
			return "未知";
		Date d = null;
		try {
			d = ft.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ft.format(d);
	}

	/**
	 * 获取两个日期相差的天数
	 */
	public static long getDayInterval(String time1, String time2) {
		long day = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			day = date1.getTime() - date2.getTime();
			day = day / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 获取两个日期相差的天数
	 * 
	 * @param time1
	 *            当前时间
	 * @param time2
	 *            要减去的时间
	 * @param time3
	 *            要减去的小时数
	 * @return
	 */
	public static long getDayInterval(String time1, String time2, float time3) {
		long day = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			day = date1.getTime() - date2.getTime();
			day = day / 1000 / 60 / 60 / 24
					- (int) (time3 / 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 获取两个日期相差的小时数
	 */
	public static long getHoursInterval(String time1, String time2) {
		long hours = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			hours = date1.getTime() - date2.getTime();
			hours = hours / 1000 / 60 / 60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hours;
	}

	/**
	 * 获取两个日期相差的小时数
	 */
	public static long getHoursInterval(String time1, String time2, float time3) {
		long hours = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			hours = date1.getTime() - date2.getTime();
			hours = hours / 1000 / 60 / 60 - (int)time3;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hours;
	}

	/**
	 * 获取两个日期相差的分钟数
	 */
	public static long getMinutesInterval(String time1, String time2) {
		long minutes = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			minutes = date1.getTime() - date2.getTime();
			minutes = minutes / 1000 / 60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return minutes;
	}

	/**
	 * 获取两个日期相差的分钟数
	 */
	public static long getMinutesInterval(String time1, String time2,
			float time3) {
		long minutes = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			minutes = date1.getTime() - date2.getTime();
			minutes = minutes / 1000 / 60
					- (int) (time3 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return minutes;
	}

	/**
	 * 获取两个日期相差的秒数
	 */
	public static long getSecondInterval(String time1, String time2) {
		long minutes = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			minutes = date1.getTime() - date2.getTime();
			minutes = minutes / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return minutes;
	}

	/**
	 * 获取两个日期相差的秒数
	 */
	public static long getSecondInterval(String time1, String time2,
			float time3) {
		long minutes = 0;
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			minutes = date1.getTime() - date2.getTime();
			minutes = minutes / 1000
					- (int) (time3 * 60 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return minutes;
	}

	/**
	 * 获取传进来时间的毫秒数
	 */
	public static long getMillisSencond(String startTime) {
		long time = 0;
		try {
			time = ft.parse(startTime).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}

}
