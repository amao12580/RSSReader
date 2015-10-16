package per.rss.server.api.cache.user.cookie;

public interface CookieCache {
	/**
	 * 保存密钥
	 * 
	 * @param cookieCiphertext
	 *            cookie值的密文
	 * @param key
	 *            解密密钥
	 * @return
	 */
	public boolean saveKey(String cookieCiphertext, int seconds, String key);

	/**
	 * 保存密钥
	 * 
	 * @param cookieCiphertext
	 *            cookie值的密文
	 * @return key 解密密钥
	 */
	public String getKey(String cookieCiphertext);

	public boolean exists(String cookieCiphertext);
}
