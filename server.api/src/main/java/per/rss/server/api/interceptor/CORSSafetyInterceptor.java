package per.rss.server.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import per.rss.core.base.util.StringUtils;
import per.rss.server.api.init.Config;
import per.rss.server.api.util.IPUtils;

/**
 * 
 * cors安全
 * 
 * preHandle调用controller具体方法之前调用， postHandle完成具体方法之后调用，
 * afterCompletion完成对页面的render以后调用
 *
 */
@Controller
public class CORSSafetyInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(CORSSafetyInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("preHandle.");
		logger.debug("ip:" + IPUtils.getRemoteIp(request));
		String sourceOrigin = request.getHeader("Origin");
		String sourceReferer = request.getHeader("referer");
		;
		logger.debug("sourceReferer:" + sourceReferer);
		if (StringUtils.isEmpty(sourceOrigin)) {
			return false;
		}
		logger.debug("Origin:" + sourceOrigin);
		String configDomain = Config.portal_webpage_domain;
		if (StringUtils.isEmpty(configDomain)) {
			return false;
		}
		logger.debug("Config:" + configDomain);
		if (!configDomain.equals(sourceOrigin)) {
			return false;
		}
		boolean checkReqTime = this.checkRequestReqTime(request);
		if (!checkReqTime) {
			return false;
		}
		setAllowCORS(response, configDomain);
		return true;// 放行
	}

	private boolean checkRequestReqTime(HttpServletRequest request) {
		String reqTime = request.getParameter("reqTime");
		if (StringUtils.isEmpty(reqTime)) {
			return false;
		}
		logger.debug("reqTime:" + reqTime);
		if (!StringUtils.isNumeric(reqTime)) {
			return false;
		}
		long reqTimeToLong = -1;
		try {
			reqTimeToLong = Long.valueOf(reqTime);
		} catch (Exception e) {
			return false;
		}
		if (reqTimeToLong <= 0) {
			return false;
		}
		long now = System.currentTimeMillis();
		if (reqTimeToLong >= now) {
			return false;
		}
		if ((now - reqTimeToLong) > 5 * 1000) {
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("postHandle.");
	}

	private void setAllowCORS(HttpServletResponse response, String configDomain) {
		// https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS
		response.addHeader("Access-Control-Allow-Origin", configDomain);
		response.addHeader("Access-Control-Allow-Methods", "GET, POST,OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "Origin,X-ITBILU,X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		response.addHeader("Access-Control-Allow-Credentials", "true");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
