package per.rss.server.api.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import per.rss.core.base.util.StringUtils;
import per.rss.server.api.bo.core.req.Req;
import per.rss.server.api.constant.InterfaceConstant;
import per.rss.server.api.controller.base.AuthPassport;
import per.rss.server.api.util.SHA256Utils;
import per.rss.server.api.util.code.impl.Base64;

/**
 * 
 * 接口调用授权方面的拦截
 *
 */
@Controller
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	private static Map<Integer, AuthPassport> handlerCache = null;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Req req = null;
		req = this.decryptRequestData(request);
		logger.debug("decryptRequestData is end.");
		if (req == null) {
			return false;
		}
		request.setAttribute(InterfaceConstant.parameterKeyPlaintext, StringUtils.toJSONString(req.getBody()));
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			logger.debug("handler hashcode is :" + handler.hashCode());
			AuthPassport authPassport = this.getAuthPassportByCache(handler);
			// 没有声明需要权限,或者声明不验证权限
			if (authPassport == null || authPassport.validate() == false) {
				logger.debug("checkHead is not need run.");
				return true;
			} else {
				// 在这里实现自己的权限验证逻辑
				boolean check = false;
				check = this.checkHead(req);
				logger.debug("checkHead is end.");
				return check;
			}
		} else {
			return false;
		}
	}

	private AuthPassport getAuthPassportByCache(Object handler) {
		if (handlerCache == null) {
			handlerCache = new HashMap<Integer, AuthPassport>(10);
		}
		int hashcode = handler.hashCode();
		if (handlerCache.containsKey(hashcode)) {
			return handlerCache.get(hashcode);
		}

		AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
		handlerCache.put(hashcode, authPassport);
		return authPassport;
	}

	private boolean checkHead(Req req) {
		logger.debug("checkHead is start.");

		return false;
	}

	/**
	 * 数据传输，请求的数据进行解密
	 * 
	 * @param parameterKey
	 * @param value
	 * @return
	 */
	private Req decryptRequestData(HttpServletRequest request) {
		logger.debug("decryptRequestData is start.");
		String value = request.getParameter(InterfaceConstant.parameterKeyCiphertext);
		if (StringUtils.isEmpty(value)) {
			logger.error("parameterValue is empty.");
			return null;
		} else {
			logger.debug("parameterValue is ：" + value);
		}
		Req req = null;
		try {
			req = StringUtils.formatJson(value, Req.class);
		} catch (Exception e) {
			logger.error("decryptRequestData is exception.", e);
			req = null;
		}
		if (req == null) {
			logger.error("req is empty.");
			return null;
		}
		logger.debug("encryptReq is ：" + StringUtils.toJSONString(req));
		req = this.decryptData(req);
		if (req != null) {
			logger.debug("decryptReq is ：" + StringUtils.toJSONString(req));
			return req;
		}
		return null;
	}

	private Req decryptData(Req req) {
		long time = req.getTime();
		if (time <= 0) {
			logger.error("time is invalid.");
			return null;
		}
		boolean checkTime = false;
		checkTime = this.checkTime(time);
		if (!checkTime) {
			logger.error("checkTime is false.");
			return null;
		}

		String dataMode = req.getDataMode();
		if (StringUtils.isEmpty(dataMode)) {
			logger.error("dataMode is empty.");
			return null;
		}
		String digest = req.getDigest();
		if (StringUtils.isEmpty(digest)) {
			logger.error("digest is empty.");
			return null;
		}
		String body = req.getBody();
		if (body == null) {
			logger.error("body is null.");
			return null;
		}
		logger.debug("body:" + body);
		String decryptBody = null;
		decryptBody = this.decryptBody(dataMode, body);
		logger.debug("decryptBody:" + decryptBody);
		if (decryptBody == null) {
			logger.error("decryptBody is null.");
			return null;
		}
		boolean checkDigest = false;
		checkDigest = this.checkDigest(digest, decryptBody);
		if (!checkDigest) {
			logger.error("checkDigest is false.");
			return null;
		}
		Req finalReq = req;
		finalReq.setBody(decryptBody);
		return finalReq;
	}

	private boolean checkDigest(String digest, String body) {
		String expectDigest = SHA256Utils.encrypt(body);
		logger.debug("digest:" + digest);
		logger.debug("expectDigest:" + expectDigest);
		if (StringUtils.isEmpty(expectDigest)) {
			logger.error("expectDigest is empty.");
			return false;
		}
		if (digest.equals(expectDigest)) {
			return true;
		}
		logger.error("Incorrect Data Summary.");
		return false;
	}

	private String decryptBody(String dataMode, String body) {
		if (dataMode.equals(InterfaceConstant.dataMode.level_one.getModel())) {
			return Base64.decrypt(body);
		} else {
			return null;
		}
	}

	private boolean checkTime(long time) {
		long now = System.currentTimeMillis();
		if (time >= now) {
			return false;
		}
		if ((now - time) > 5 * 1000) {
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
