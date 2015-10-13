package per.rss.server.api.biz.user.login.impl;

import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.rss.core.base.util.StringUtils;
import per.rss.server.api.biz.user.login.LoginBiz;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.bo.core.RSA;
import per.rss.server.api.bo.core.Resp;
import per.rss.server.api.bo.user.login.LoginBo;
import per.rss.server.api.bo.user.login.ToLoginBo;
import per.rss.server.api.dao.user.login.AccountDao;
import per.rss.server.api.filter.csrf.CSRFTokenManager;
import per.rss.server.api.util.PasswordUtils;
import per.rss.server.api.util.rsa.RSAUtil;

/**
 *
 */
@Service(value = "loginBiz")
public class LoginBizImpl implements LoginBiz {
	private static final Logger logger = LoggerFactory.getLogger(LoginBizImpl.class);
	@Autowired
	private AccountDao accountDao;

	@Override
	public Resp doUserLogin(LoginBo loginBo) {
		Resp resp = null;
		try {
			String username = loginBo.getUsername();
			if (StringUtils.isEmpty(username)) {
				logger.error("username is empty.");
				resp = new Resp(Error.message.user_login_username_isEmpty);
				return resp;
			}
			username = RSAUtil.decrypt(username);// 传输解密
			if (StringUtils.isEmpty(username)) {
				logger.error("username is decryption error.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}

			String password = loginBo.getPassword();
			if (StringUtils.isEmpty(password)) {
				logger.error("password is empty.");
				resp = new Resp(Error.message.user_login_username_isEmpty);
				return resp;
			}
			password = RSAUtil.decrypt(password);// 传输解密
			if (StringUtils.isEmpty(password)) {
				logger.error("password is decryption error.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}

			String uid = accountDao.findUidByLogin(username, PasswordUtils.doEncryption(password));
			if (StringUtils.isEmpty(uid)) {
				logger.error("username or password not match.");
				resp = new Resp(Error.message.user_login_usernameOrPassword_notMatch);
				return resp;
			}
			logger.debug("uid is :" + uid);
			resp = new Resp();
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

	@Override
	public Resp getRSAKey(HttpSession session) {
		Resp resp = null;
		try {
			RSAPublicKey rsap = (RSAPublicKey) RSAUtil.getKeyPair().getPublic();
			if (rsap == null) {
				logger.error("rsap is empty.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}
			String module = rsap.getModulus().toString(16);
			if (StringUtils.isEmpty(module)) {
				logger.error("module is empty.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}
			String empoent = rsap.getPublicExponent().toString(16);
			if (StringUtils.isEmpty(empoent)) {
				logger.error("empoent is empty.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}
			if (session == null) {
				logger.error("session is empty.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}
			ToLoginBo bo = new ToLoginBo();
			bo.setRsa(new RSA(module, empoent));
			String csrfToken = CSRFTokenManager.getTokenForSession(session);
			if (StringUtils.isEmpty(csrfToken)) {
				logger.error("csrfToken is empty.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}
			bo.setCsrfToken(csrfToken);
			resp = new Resp(bo);
			// logger.debug("resp is :" + StringUtils.toJSONString(resp));
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
}
