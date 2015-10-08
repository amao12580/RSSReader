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

	public static boolean isValid(Date date) {
		if (date == null || date.equals(CommonConstant.dateBegining)) {
			return false;
		}else{
			return true;
		}
	}

}
