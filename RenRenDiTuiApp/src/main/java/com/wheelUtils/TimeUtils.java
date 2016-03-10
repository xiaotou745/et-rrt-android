package com.wheelUtils;
/*
 * File Name: TimeUtils.java 
 * History:
 * Created by Administrator on 2015-9-18
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具 (Description)
 * 
 * @author zaokafei
 * @version 1.0
 * @date 2015-9-18
 */
public class TimeUtils {
	/**
	 * 返回系统当前的完整日期时间 <br>
	 * 格式 1：2008-05-02 13:12:44 <br>
	 * 格式 2：2008/05/02 13:12:44 <br>
	 * 格式 3：2008年5月2日 13:12:44 <br>
	 * 格式 4：2008年5月2日 13时12分44秒 <br>
	 * 格式 5：2008年5月2日 星期五 13:12:44 <br>
	 * 格式 6：2008年5月2日 星期五 13时12分44秒 <br>
	 * 
	 * @param 参数
	 *            (formatType) :格式代码号
	 * @return 字符串
	 */
	public static String get(int formatType) {
		SimpleDateFormat sdf = null;
		if (formatType == 1) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (formatType == 2) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if (formatType == 3) {
			sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		} else if (formatType == 4) {
			sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		} else if (formatType == 5) {
			sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH:mm:ss");
		} else if (formatType == 6) {
			sdf = new SimpleDateFormat("yyyy年MM月dd日 E HH时mm分ss秒");
		}
		return sdf.format(new Date());
	}

	/**
	 * 获取当前系统年
	 *
	 * @param isC
	 * @return
	 */
	public static String getYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split("-")[0];
	}

	/**
	 * 返回：当前系统月份
	 *
	 * @return 09
	 */
	public static String getMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split("-")[1];
	}

	/**
	 * 返回：当前系统日
	 *
	 * @return 09
	 */
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split("-")[2].split(" ")[0];
	}

	/**
	 * 返回：当前系统日
	 *
	 * @return 09
	 * @throws ParseException
	 */
	public static String getDateStr(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		date = sdf.parse(str);
		return sdf.format(date);
	}
	/**
	 * 返回：当前系统日
	 *
	 * @return 09
	 * @throws ParseException
	 */
	public static String getDateStrNew(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		date = sdf.parse(str);
		return sdf.format(date);
	}

	public static String setDateStr(boolean isToday) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		if (!isToday) {
			date.setDate(date.getDate() + 1);
		}
		return sdf.format(date);
	}

	/**
	 * 返回：系统当前日期
	 *
	 * @return 2009-09-09
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split(" ")[0];
	}

	/**
	 * 返回：系统当前时间
	 *
	 * @return 09:09:09
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split(" ")[1];
	}

	/**
	 * 返回：系统当前完整日期时间
	 *
	 * @return 2009-09-09 09:09:09
	 */
	public static String getCurrentFullTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 返回：系统当前时间时
	 *
	 * @return 09
	 */
	public static String getHours() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split(" ")[1].split(":")[0];
	}

	/**
	 * 返回：系统当前时间分
	 *
	 * @return 12
	 */
	public static String getMinutes() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split(" ")[1].split(":")[1];
	}

	/**
	 * 返回：系统当前时间秒
	 *
	 * @return 12
	 */
	public static String getSeconds() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date()).split(" ")[1].split(":")[2];
	}

	/**
	 * 返回：系统当前年月
	 *
	 * @return 12
	 */
	public static String getYearAndMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date());
	}

	/**
	 * 返回：系统下一个日
	 *
	 * @return 12
	 */
	public static String getNextDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
			newDate.setDate(newDate.getDate() + 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(newDate);
	}

	/**
	 * 返回：系统前日
	 *
	 * @return 12
	 */
	public static String getBeforeDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
			newDate.setDate(newDate.getDate() - 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(newDate);
	}

	/**
	 * 返回：系统上一个月
	 *
	 * @return 12
	 */
	public static String getBeforeMonth(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
			newDate.setMonth(newDate.getMonth() - 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(newDate);
	}

	/**
	 * 返回：系统下一个月
	 *
	 * @return 12
	 */
	public static String getNextMonth(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
			newDate.setMonth(newDate.getMonth() + 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(newDate);
	}

	/**
	 * 返回：系统前日
	 * 
	 * @return 12
	 */
	public static boolean compareDate(boolean rightFlag, boolean dateType, String date) {
		boolean flag = false;
		SimpleDateFormat sdf = null;
		String nowDateStr = "";
		Date dateNow = null;
		Date oldDate = null;
		Date smallDate = null;
		try {

			if (dateType) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				smallDate = sdf.parse("2014-01-01");
				oldDate = sdf.parse(date);

			} else {
				sdf = new SimpleDateFormat("yyyy-MM");
				smallDate = sdf.parse("2014-01");
				oldDate = sdf.parse(date);
				oldDate.setMonth(oldDate.getMonth());

			}
			nowDateStr = sdf.format(new Date());
			dateNow = sdf.parse(nowDateStr);
			if (rightFlag) {
				if (oldDate.getTime() < dateNow.getTime()) {
					flag = true;
				}
			} else {
				if (oldDate.getTime() > smallDate.getTime()) {
					flag = true;
				}
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return flag;
	}

	/**
	 * 返回：系统前日
	 * 
	 * @return 12
	 */
	public static boolean equalsDate(boolean rightFlag, boolean dateType, String date) {
		boolean flag = false;
		SimpleDateFormat sdf = null;
		String nowDateStr = "";
		Date dateNow = null;
		Date oldDate = null;
		Date smallDate = null;
		try {

			if (dateType) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				smallDate = sdf.parse("2014-01-01");
				oldDate = sdf.parse(date);

			} else {
				sdf = new SimpleDateFormat("yyyy-MM");
				smallDate = sdf.parse("2014-02");
				oldDate = sdf.parse(date);

			}
			nowDateStr = sdf.format(new Date());
			dateNow = sdf.parse(nowDateStr);
			if (dateType) {
				dateNow.setDate(dateNow.getDate() - 1);
			} else {
				dateNow.setMonth(dateNow.getMonth() - 1);
			}

			if (rightFlag) {
				if (oldDate.getTime() == dateNow.getTime()) {
					flag = true;
				}
			} else {
				if (oldDate.getTime() == smallDate.getTime()) {
					flag = true;
				}
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return flag;
	}

	/**
	 * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11",
	 * "yyyy-MM-dd","yyyy年MM月dd日").
	 * 
	 * @param date
	 *            String 想要格式化的日期
	 * @param oldPattern
	 *            String 想要格式化的日期的现有格式
	 * @param newPattern
	 *            String 想要格式化成什么格式
	 * @return String
	 */
	public static final String StringPattern(String date, String oldPattern, String newPattern) {
		if (date == null || oldPattern == null || newPattern == null)
			return "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern); // 实例化模板对象
		SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern); // 实例化模板对象
		Date d = null;
		try {
			d = sdf1.parse(date); // 将给定的字符串中的日期提取出来
		} catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理
			e.printStackTrace(); // 打印异常信息
		}
		return sdf2.format(d);
	}
}
