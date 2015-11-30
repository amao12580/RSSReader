package per.rss.server.api.cache.rsa.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import per.rss.core.base.util.UUIDUtils;
import per.rss.core.cache.util.JedisUtil;
import per.rss.server.api.cache.rsa.RSACache;

@Repository(value = "RSACache")
public class RSACacheImpl implements RSACache {
	private static final Logger logger = LoggerFactory.getLogger(RSACacheImpl.class);

	private static final String thisCachePath_Prefix = "rsa.info";

	@Autowired(required = true)
	private JedisUtil jedisUtil;

	@Override
	public boolean save(String rsaInfo) {
		String testkey = UUIDUtils.randomUUID();
		String testvalue = System.currentTimeMillis() + "";
		boolean testResult = jedisUtil.add(thisCachePath_Prefix + testkey, thisCacheExpired_Seconds, testvalue);
		logger.debug("testkey:" + testkey);
		logger.debug("testvalue:" + testvalue);
		logger.debug("testResult:" + testResult);
		logger.debug("testCache:" + jedisUtil.get(thisCachePath_Prefix + testkey));

		return jedisUtil.add(thisCachePath_Prefix, thisCacheExpired_Seconds, rsaInfo);
	}

	@Override
	public String get() {
		return jedisUtil.get(thisCachePath_Prefix);
	}
}
