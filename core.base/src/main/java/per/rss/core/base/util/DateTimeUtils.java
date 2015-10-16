package per.rss.core.base.util;

import java.util.Date;

import org.joda.time.DateTime;

import per.rss.core.base.constant.CommonConstant;

/**
 * http://blog.csdn.net/fengyuzhengfan/article/details/40164721
 * 
 */
public class DateTimeUtils {
	private static final String DEFAULT_MASK = "yyyy-MM-dd HH:mm:ss.SSS";

	public static String formatDateTime(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(DEFAULT_MASK);
	}

	/**
	 * 当前时间加减天数后的时间
	 * 
	 * @param day
	 * @return
	 */
	public static Date plusDays(int day) {
		if (day == 0) {
			return new Date();
		}
		return new DateTime().plusDays(day).toDate();
	}

	public static boolean isValid(Date date) {
		if (date == null || date.equals(CommonConstant.dateBegining)) {
			return false;
		} else {
			return true;
		}
	}
	public static void main(String[] args) {
		System.out.println(formatDateTime(new Date(1445066163*1000)));
		System.out.println(formatDateTime(plusDays(3)));
		System.out.println(plusDays(3).getTime());
		System.out.println(plusDays(3).getTime()/1000);
		System.out.println((DateTimeUtils.plusDays(3).getTime()-(new Date().getTime())) / 1000);
	}

}
