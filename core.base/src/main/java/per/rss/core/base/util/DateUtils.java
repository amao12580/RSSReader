package per.rss.core.base.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtils {
	
	public static String datetoString(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
	}
}
