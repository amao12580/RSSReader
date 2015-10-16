package per.rss.server.api.biz.user.login.impl;

import java.security.Key;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.server.api.biz.user.login.LoginBiz;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.bo.core.RSA;
import per.rss.server.api.bo.core.Resp;
import per.rss.server.api.bo.user.login.LoginBo;
import per.rss.server.api.bo.user.login.LoginSuccessCookieBo;
import per.rss.server.api.bo.user.login.ToLoginBo;
import per.rss.server.api.cache.user.cookie.CookieCache;
import per.rss.server.api.dao.user.login.AccountDao;
import per.rss.server.api.filter.csrf.CSRFTokenManager;
import per.rss.server.api.util.IPUtils;
import per.rss.server.api.util.PasswordUtils;
import per.rss.server.api.util.aes.AESUtil;
import per.rss.server.api.util.rsa.RSAUtil;

/**
 *
 */
@Service(value = "loginBiz")
public class LoginBizImpl implements LoginBiz {
	private static final Logger logger = LoggerFactory.getLogger(LoginBizImpl.class);
	@Autowired(required = true)
	private AccountDao accountDao;
	@Autowired(required = true)
	private CookieCache cookieCache;

	@Override
	public Resp doUserLogin(LoginBo loginBo, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
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
			if (!doSuccessLogin(uid, request, response)) {
				logger.error("doSuccessLogin error.");
				resp = new Resp(Error.message.system_exception);
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
		if (resp.getCode() != Error.message.common_success.getCode()) {
			// 刷新csrfToken
			CSRFTokenManager.getTokenForSession(session);
		}
		return resp;
	}

	private boolean doSuccessLogin(String uid, HttpServletRequest request, HttpServletResponse response) {
		return setSafetyCookie(uid, request, response);
	}

	private boolean setSafetyCookie(String uid, HttpServletRequest request, HttpServletResponse response) {
		String clientIP = IPUtils.getRemoteIp(request);
		if (StringUtils.isEmpty(clientIP)) {
			logger.error("clientIP is empty.");
			return false;
		}
		logger.debug("clientIP:" + clientIP);

		String cookieValue = null;
		byte[] key = AESUtil.initSecretKey();
		if (StringUtils.isEmpty(AESUtil.showByteArray(key))) {
			logger.error("AES byte key is empty.");
			return false;
		}
		Key k = AESUtil.toKey(key);
		if (k == null) {
			logger.error("AES key is empty.");
			return false;
		}
		logger.debug("create encode key is:" + StringUtils.toJSONString(k));
		LoginSuccessCookieBo bo = new LoginSuccessCookieBo();
		bo.setUid(uid);
		bo.seteTime(CommonConstant.LOGINSUCCESSCLIENTCOOIKEEXPIRE + System.currentTimeMillis());
		bo.setIp(clientIP);

		String data = StringUtils.toJSONString(bo);
		byte[] encryptData = AESUtil.encrypt(data.getBytes(), k);
		if (StringUtils.isEmpty(AESUtil.showByteArray(encryptData))) {
			logger.error("encryptData is empty.");
			return false;
		}
		cookieValue = Hex.encode(encryptData).toString();
		// 保存cookie
		if (!cookieCache.saveKey(cookieValue, CommonConstant.LOGINSUCCESSCLIENTCOOIKEEXPIRE,
				StringUtils.toJSONString(k))) {
			logger.error("save cookie is error.");
			return false;
		}
		// 为客户端设置cookie
		Cookie cookie2 = new Cookie(CommonConstant.LOGINSUCCESSCLIENTCOOIKENAME, cookieValue);
		String path = request.getContextPath();
		if (StringUtils.isEmpty(path)) {
			return false;
		}
		cookie2.setComment("Welcome to use :" + path);
		// 如果设置为负值的话，则为浏览器进程Cookie(内存中保存)，关闭浏览器就失效。
		// 如果设置为 0 的话，则该cookie会被删除。
		cookie2.setMaxAge(CommonConstant.LOGINSUCCESSCLIENTCOOIKEEXPIRE);// 3天后过期，单位：秒
		cookie2.setHttpOnly(true);
		cookie2.setPath(path);
		// setSecure(true); 的情况下，只有https才传递到服务器端。http是不会传递的。
		// cookie2.setSecure(true);
		response.addCookie(cookie2);
		return true;
	}

	// private void cookieToString(Cookie cookie) {
	// logger.debug("getComment:" + cookie.getComment());
	// logger.debug("getDomain:" + cookie.getDomain());
	// logger.debug("getMaxAge:" + cookie.getMaxAge());
	// logger.debug("getName:" + cookie.getName());
	// logger.debug("getPath:" + cookie.getPath());
	// logger.debug("getValue:" + cookie.getValue());
	// logger.debug("getVersion:" + cookie.getVersion());
	// logger.debug("getSecure:" + cookie.getSecure());
	// }

	@Override
	public Resp toLogin(HttpSession session) {
		Resp resp = null;
		try {
			RSA rsa = RSAUtil.rsa;
			if (rsa == null) {
				logger.error("rsa is empty.");
				resp = new Resp(Error.message.system_exception);
				return resp;
			}
			ToLoginBo bo = new ToLoginBo();
			bo.setRsa(rsa);
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
