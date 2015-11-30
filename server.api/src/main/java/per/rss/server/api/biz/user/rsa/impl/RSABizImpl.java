package per.rss.server.api.biz.user.rsa.impl;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.rss.core.base.util.StringUtils;
import per.rss.server.api.biz.user.rsa.RSABiz;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.bo.core.RSA;
import per.rss.server.api.bo.core.resp.Resp;
import per.rss.server.api.cache.rsa.RSACache;
import per.rss.server.api.util.rsa.RSAUtil;

/**
 *
 */
@Service(value = "RSABiz")
public class RSABizImpl implements RSABiz {
	private static final Logger logger = LoggerFactory.getLogger(RSABizImpl.class);
	@Autowired(required = true)
	private RSACache RSACache;

	@Override
	public Resp update() {
		Resp resp = null;
		try {
			long cachestartTime = System.currentTimeMillis();
			String rsaStr = RSACache.get();
			if (StringUtils.isEmpty(rsaStr)) {
				logger.debug("cache is not found.time is :" + (System.currentTimeMillis() - cachestartTime));
				rsaStr = this.createRSAInfo();
				if (StringUtils.isEmpty(rsaStr)) {
					logger.error("rsaStr is empty.");
					resp = new Resp(Error.message.system_exception);
					return resp;
				}
				boolean saveRet = RSACache.save(rsaStr);
				if (!saveRet) {
					logger.error("saveRet is false.");
					resp = new Resp(Error.message.system_exception);
					return resp;
				}
			}else{
				logger.debug("cache is hitted.time is :" + (System.currentTimeMillis() - cachestartTime));
			}
			RSA rsa = null;
			rsa = this.getPublicKeys(rsaStr);
			if (rsa == null) {
				logger.error("rsa is null.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}
			resp = new Resp(rsa);
		} catch (Throwable e) {
			logger.error("The server appeared abnormal.", e);
			resp = new Resp(Error.message.system_exception);
		} finally {
			if (resp == null) {
				logger.error("Unpredictable problem happened.");
				resp = new Resp(Error.message.system_error);
			}
		}
		return resp;
	}

	private RSA getPublicKeys(String rsaStr) {
		KeyPair keyPair = RSAUtil.Bin2KeyPair(rsaStr);
		if (keyPair == null) {
			logger.error("keyPair is null.");
			return null;
		}
		RSAPublicKey rsapk = (RSAPublicKey) keyPair.getPublic();
		if (rsapk == null) {
			logger.error("rsapk is null.");
			return null;
		}
		BigInteger BigInteger_module = rsapk.getModulus();
		if (BigInteger_module == null) {
			logger.error("BigInteger_module is null.");
			return null;
		}
		BigInteger BigInteger_empoent = rsapk.getPublicExponent();
		if (BigInteger_empoent == null) {
			logger.error("BigInteger_empoent is null.");
			return null;
		}
		RSA rsa = new RSA();
		rsa.setModule(BigInteger_module.toString());
		rsa.setEmpoent(BigInteger_empoent.toString());
		return rsa;
	}

	private String createRSAInfo() {
		KeyPair keyPair = RSAUtil.generateKeyPair();
		if (keyPair != null) {
			return RSAUtil.KeyPair2Bin(keyPair);
		}
		return null;
	}
}
