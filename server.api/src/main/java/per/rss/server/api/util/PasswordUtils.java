package per.rss.server.api.util;

import per.rss.core.base.util.MD5Utils;

public class PasswordUtils {
	/**
	 * 密码存储加密
	 * @param plaintext
	 * @return
	 */
	public static String doEncryption(String plaintext) {
		return MD5Utils.code(plaintext);
	}
}
