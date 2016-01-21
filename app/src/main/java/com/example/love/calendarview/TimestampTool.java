package com.example.love.calendarview;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimestampTool {

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 
	 * @param timeStryyyy-MM-dd
	 *            HH:mm:ss
	 * @return
	 */
	public static long getSomdayTime(String timeStr) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(timeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getTimeInMillis();
	}

	/**
	 * 当前时间
	 * 
	 * @return Timestamp
	 */
	public static Timestamp crunttime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 将事件转换成服务端解析时间
	 * 
	 * @param dt
	 * @return
	 */
	public static String praseDateToWebDate(String dt) {
		Date jDt = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (dt.length() > 16) {
				jDt = sdf.parse(dt);
			} else if (dt.length() > 10) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				jDt = sdf.parse(dt);
			} else if (dt.length() > 0) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				jDt = sdf.parse(dt);
			} else {
				jDt = null;
			}
		} catch (Exception ex) {
			jDt = null;
		}
		return sf.format(jDt);
	}

	/**
	 * 获取当前时间的字符串 用来提交给web端
	 * 
	 * @return String ex:2013-11-21 00:00:00 +0800
	 */
	public static String getCurrentDateToWeb() {
		StringBuffer sbf = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// SimpleDateFormat sdf2 = new SimpleDateFormat("Z");
		// sbf.append(sdf.format(new Date())).append(" 00:00:00
		// ").append(sdf2.format(new Date()));
		sbf.append(sdf.format(new Date())).append(" 00:00:00");
		return sbf.toString();
	}

	/*
	 *20150924->2015-09-24 00:00:00
	 */
	public static String dateToDate(String date){
		return date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" 00:00:00";
	}

	/**
	 * 获取当前时间的字符串
	 * 
	 * @return String ex:2006-07-07
	 */
	public static String getCurrentDate() {
		Timestamp d = crunttime();
		return d.toString().substring(0, 10);
	}

	/**
	 * 获取当前时间的字符串
	 *
	 * @return String ex:2006-07-07  ->  2006年07月07日
	 */
	public static String dateToCnDate1() {
		Timestamp d = crunttime();
		String[]arr = d.toString().substring(0, 10).split("-");
		return arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
	}

	/**
	 * 获取当前时间的字符串
	 *
	 * @return String ex:2006-07-07  ->  2006年7月7日
	 */
	public static String dateToCnDate2() {
		Timestamp d = crunttime();
		String[]arr = d.toString().substring(0, 10).split("-");
		return Integer.parseInt(arr[1])+"月"+Integer.parseInt(arr[2])+"日";
	}

	/**
	 * 获取当前时间的字符串
	 *
	 * @return String ex:2006-07-07  ->  2006年7月7日
	 */
	public static String dateToCnDate2(String date) {
		String[]arr = date.split("-");
		return Integer.parseInt(arr[1])+"月"+Integer.parseInt(arr[2])+"日";
	}


	/**
	 * 获取当前时间的字符串
	 * 
	 * @return String ex:2006-07-07 22:10:10 z
	 */
	public static String getCurrentDateTime() {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 获取指定时间的字符串,只到日期
	 * 
	 * @param t
	 *            Timestamp
	 * @return String ex:2006-07-07
	 */
	public static String getStrDate(Timestamp t) {
		return t.toString().substring(0, 10);
	}

	/**
	 * 获取指定时间的字符串
	 * 
	 * @param t
	 *            Timestamp
	 * @return String ex:2006-07-07 22:10:10
	 */
	public static String getStrDateTime(Timestamp t) {
		return t.toString().substring(0, 19);
	}

	/**
	 * 获得当前日期的前段日期
	 * 
	 * @param days
	 * @return String
	 */
	public static String getStrIntervalDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -Integer.parseInt(days));
		String strBeforeDays = sdf.format(cal.getTime());
		return strBeforeDays;
	}

	/**
	 * 格式化时间
	 * 
	 * @param dt
	 *            String -> yyyy-MM-dd hh:mm:ss
	 * @return java.util.Date.Date -> yyyy-MM-dd hh:mm:ss
	 */
	public static Date parseDateTime(String dt) {
		Date jDt = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (dt.length() > 16) {
				jDt = sdf.parse(dt);
			} else if (dt.length() > 10) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				jDt = sdf.parse(dt);
			} else if (dt.length() > 0) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				jDt = sdf.parse(dt);
			} else {
				jDt = null;
			}
		} catch (Exception ex) {
			jDt = null;
		}
		return jDt;
	}

	/**
	 * 格式化时间yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            java.util.Date
	 * @return String -> yyyy-MM-dd HH:mm:ss
	 */
	public static String parseDateTime(Date date) {
		String s = null;
		try {
			if (date != null) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				s = f.format(date);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 格式化时间yyyy-MM-dd HH:mm:ss.fff
	 * 
	 * @param date
	 *            java.util.Date
	 * @return String -> yyyy-MM-dd HH:mm:ss.fff
	 */
	public static String parseDateTimeMs(Date date) {
		String s = null;
		try {
			if (date != null) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff");
				s = f.format(date);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String parseDateTime2HHmm(Date date) {
		String s = null;
		try {
			if (date != null) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				s = f.format(date);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String parseDateOtherFormat(Date date) {
		String s = null;
		try {
			if (date != null) {
				SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
				s = f.format(date);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 格式化时间yyyy
	 * 
	 * @param date
	 *            java.util.Date
	 */
	public static String parseDateYYYY(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 格式化时间yyyyMMdd
	 * 
	 * @param date
	 *            java.util.Date
	 */
	public static String parseDateYYYYMMDD(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 格式化日期
	 * 
	 * @param dt
	 *            String -> yyyy-MM-dd
	 * @return java.util.Date.Date -> yyyy-MM-dd
	 */
	public static Date parseDate(String dt) {
		Date jDt = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (dt.length() >= 8) {
				jDt = sdf.parse(dt);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	public static Date parseOtherFormatDate(String dt) {
		Date jDt = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if (dt.length() >= 8) {
				jDt = sdf.parse(dt);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	/**
	 * 格式化时间yyyy-MM-dd
	 * 
	 * @param date
	 *            java.util.Date
	 * @return String -> yyyy-MM-dd
	 */
	public static String parseDate(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 
	 * @param dt
	 * @return String
	 */
	public static String getLongDateFromShortDate(String dt) {
		String strDT = dt;
		try {
			if (strDT != null && strDT.length() <= 10) {
				strDT = dt.trim() + " 00:00:00";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strDT;
	}

	/**
	 * 
	 * @param dt
	 * @return String
	 */
	public static String getShortDateToHHMM(String dt) {
		String jDt = dt;
		try {
			if (jDt != null && jDt.length() <= 10) {
				jDt = jDt + " 00:00";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			jDt = sdf.parse(jDt).toLocaleString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	/**
	 * 
	 * @param dateStr
	 * @return String
	 */
	public static String formatDateToHHMM(String dateStr) {
		String resultDate = null;
		try {
			if (dateStr.length() > 10) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date = sdf.parse(dateStr);
				resultDate = sdf.format(date);
			} else
				resultDate = dateStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	/**
	 * 返回日期 格式:2006-07-05
	 * 
	 * @param str
	 * @return Timestamp
	 */
	public static Timestamp date(String str) {
		Timestamp tp = null;
		if (str.length() <= 10) {
			String[] string = str.trim().split("-");
			int one = Integer.parseInt(string[0]) - 1900;
			int two = Integer.parseInt(string[1]) - 1;
			int three = Integer.parseInt(string[2]);
			tp = new Timestamp(one, two, three, 0, 0, 0, 0);
		}
		return tp;
	}

	/**
	 * 返回时间和日期 格式:2006-07-05 22:10:10
	 * 
	 * @param str
	 * @return Timestamp
	 */
	public static Timestamp datetime(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			int time3 = Integer.parseInt(time[2]);
			tp = new Timestamp(date1, date2, date3, time1, time2, time3, 0);
		}
		return tp;
	}

	/**
	 * 返回日期和时间(没有秒) 格式:2006-07-05 22:10
	 * 
	 * @param str
	 * @return Timestamp
	 */
	public static Timestamp datetimeHm(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
		}
		return tp;
	}

	/**
	 * 获得当前系统日期与本周一相差的天数
	 * 
	 * @return int
	 */
	private static int getMondayPlus() {
		Calendar calendar = Calendar.getInstance();
		// 获得今天是一周的第几天，正常顺序是星期日是第一天，星期一是第二天......
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 星期日是第一天
		return (dayOfWeek == 1) ? -6 : 2 - dayOfWeek;
	}

	/**
	 * 获得距当前时间所在某星期的周一的日期 例： 0-本周周一日期 -1-上周周一日期 1-下周周一日期
	 * 
	 * @param week
	 *            int
	 * @return java.util.Date
	 */
	public static Date getMondayOfWeek(int week) {
		int mondayPlus = getMondayPlus(); // 相距周一的天数差
		GregorianCalendar current = new GregorianCalendar();
		current.add(GregorianCalendar.DATE, mondayPlus + 7 * week);
		return current.getTime();
	}

	/**
	 * 获得某日前后的某一天
	 * 
	 * @param date
	 *            java.util.Date
	 * @param day
	 *            int
	 * @return java.util.Date
	 */
	public static Date getDay(Date date, int day) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(GregorianCalendar.DATE, day);
		return c.getTime();
	}

	/**
	 * 获得距当前周的前后某一周的日期
	 * 
	 * @param week
	 *            int
	 * @return String[]
	 */
	public static String[] getDaysOfWeek(int week) {
		String[] days = new String[7];
		Date monday = getMondayOfWeek(week); // 获得距本周前或后的某周周一
		Timestamp t = new Timestamp(monday.getTime());
		days[0] = getStrDate(t);
		for (int i = 1; i < 7; i++) {
			t = new Timestamp(getDay(monday, i).getTime());
			days[i] = getStrDate(t);
		}
		return days;
	}

	/***
	 * MCC的UTC时间转换，MCC的UTC不是到毫秒的
	 * 
	 * @param utc
	 * @return java.util.Date
	 */
	public static Date mccUTC2Date(long utc) {
		Date d = new Date();
		d.setTime(utc * 1000); // 转成毫秒
		return d;
	}

	public static long nowDate2UTC() {
		Date d = new Date();
		return (long) d.getTime() / (long) 1000;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(parseDateTime(utc2Date(1279701969)));
	 * System.out.println(System.currentTimeMillis()); }
	 */
	// 获取当前日期之前的日期字符串 如 2007-04-15 前5月 就是 2006-11-15
	public static String getPreviousMonth(int month) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.MONTH, -month);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal1.getTime());
	}

	public static String getStrYear(int year) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.YEAR, -year);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(cal1.getTime()) + "年份";
	}

	/**
	 * 比较两个日期前后 可以大于或等于
	 * 
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static boolean compareTwoDays(String starDate, String endDate) {
		Calendar cal_start = Calendar.getInstance();
		Calendar cal_end = Calendar.getInstance();
		cal_start.setTime(parseDate(starDate));
		cal_end.setTime(parseDate(endDate));
		return cal_end.after(cal_start);
	}

	public static boolean compareTwoDate(Date d1, Date d2) {
		if (d1 != null && d2 != null) {
			Calendar cal_start = Calendar.getInstance();
			Calendar cal_end = Calendar.getInstance();
			cal_start.setTime(d1);
			cal_end.setTime(d2);
			return cal_end.after(cal_start);
		}
		return false;
	}

	public static int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	// 得到两个日期之间的年
	public static int dateDiffYear(String starDate, String endDate) {
		int result = 0;
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d1.setTime(parseDate(starDate));
		d2.setTime(parseDate(endDate));

		// 日期大小翻转
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int yy = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
		int mm = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
		if (mm < 0) {
			result = yy - 1;
		}
		if (mm > 0) {
			result = yy;
		}
		if (mm == 0) {
			if ((d2.getTimeInMillis() - d1.getTimeInMillis()) >= 0) {
				result = yy;
			} else {
				result = yy - 1;
			}
		}
		return result;
	}

	// 得到两个日期之间的月，不满一月不算
	public static int dateDiffMonth(String starDate, String endDate) {
		int result = 0;
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d1.setTime(parseDate(starDate));
		d2.setTime(parseDate(endDate));

		// 日期大小翻转
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int yy = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
		int mm = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
		int dd = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		result = yy * 12 + mm;
		if (dd < 0) {
			result = result - 1;
		}
		return result;
	}
}
