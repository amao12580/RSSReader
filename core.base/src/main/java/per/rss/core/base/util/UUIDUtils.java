package per.rss.core.base.util;

import java.util.UUID;

public class UUIDUtils {
	public static String randomUUID() {
		String str = UUID.randomUUID().toString();
		// 去掉"-"符号
		return (str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
		+ str.substring(24));
	}
}
