package per.rss.core.base.util;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * http://blog.csdn.net/fengyuzhengfan/article/details/40164721
 * 
 * @author cifpay
 *
 */
public class DateTimeUtils {
	private static final String DEFAULT_MASK = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static String formatDateTime(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(DEFAULT_MASK);
	}

}
