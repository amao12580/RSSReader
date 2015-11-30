package per.rss.core.cache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.JedisCluster;

@Repository(value = "jedisUtil")
public class JedisUtil {

	@Autowired
	private JedisCluster jedisCluster;

	public boolean add(final String key, final int seconds, final String value) {
		String result = jedisCluster.setex(key, seconds, value);
		if (result == null || "".equals(result)) {
			return false;
		}
		if ("OK".equals(result)) {
			return true;
		} else {
			return false;
		}
	}

	public String get(final String key) {
		return jedisCluster.get(key);
	}

	public boolean exists(final String key) {
		return jedisCluster.exists(key);
	}

	public boolean delete(final String key) {
		long counts = jedisCluster.del(key);
		if (counts > 0) {
			return true;
		}
		return false;
	}
}
