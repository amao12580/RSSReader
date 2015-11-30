package per.rss.server.api.cache.rsa;

public interface RSACache {
	public static final int thisCacheExpired_Seconds = 24*60*60;//默认有效时间为一天
	/**
	 * 保存密钥
	 */
	public boolean save(String rsaInfo);

	/**
	 * 获取密钥
	 */
	public String get();
}
