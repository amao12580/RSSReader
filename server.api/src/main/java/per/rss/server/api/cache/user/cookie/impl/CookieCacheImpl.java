package per.rss.server.api.cache.user.cookie.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import per.rss.core.cache.util.JedisUtil;
import per.rss.server.api.cache.user.cookie.CookieCache;

@Repository(value = "cookieCache")
public class CookieCacheImpl implements CookieCache {
	private static final String thisCachePath_Prefix = "user.login.cookie.";

	@Autowired(required = true)
	private JedisUtil jedisUtil;

	@Override
	public boolean saveKey(String cookieCiphertext, int seconds, String key) {
		return jedisUtil.add(thisCachePath_Prefix + cookieCiphertext, seconds, key);
	}

	@Override
	public String getKey(String cookieCiphertext) {
		return jedisUtil.get(thisCachePath_Prefix + cookieCiphertext);
	}

	@Override
	public boolean exists(String cookieCiphertext) {
		return jedisUtil.exists(thisCachePath_Prefix + cookieCiphertext);
	}
}
