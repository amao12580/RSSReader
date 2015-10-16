package per.rss.server.api.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.bo.core.Resp;
import per.rss.server.api.cache.user.cookie.CookieCache;

/**
 * 
 * 强制验证当前用户Token拦截器
 * 
 * 检查用户Token信息，token无效则请求被拒绝
 * 
 * @author cifpay
 *
 */
@Controller
public class UserLoginTokenInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(UserLoginTokenInterceptor.class);

	@Autowired(required = true)
	private CookieCache cookieCache;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("To verify the current user's token.");
		String token = null;
		Resp resp = null;
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return false;
		for (Cookie cookie : cookies) {
			logger.debug("cookie name=" + cookie.getName());
			logger.debug("cookie value=" + cookie.getValue());
			if (CommonConstant.LOGINSUCCESSCLIENTCOOIKENAME.equals(cookie.getName())) {
				token = cookie.getValue();
				logger.debug("token=" + token);
				break;
			}
		}
		if (token == null) {// request 中 相关token 无法获取
			logger.error("Token is not found.");
			resp = new Resp(Error.message.user_login_token_error);
			this.responseError(request, resp, response);
			// request.getRequestDispatcher("/login.jsp").forward(request,
			// response);//强制跳转
			return false;// 拒绝处理
		}
		boolean exist = cookieCache.exists(token);
		if (!exist) {
			logger.error("oldCookie is not exist.");
			resp = new Resp(Error.message.user_login_token_error);
			this.responseError(request, resp, response);
			return false;// 拒绝处理
		} else {
			logger.debug("cookie decode key is :" + cookieCache.getKey(token));
		}
		return true;// 放行
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * 出现错误，输出详细错误信息
	 * 
	 * @param request
	 * @param response
	 * @return true表示输出成功，false表示输出失败
	 */
	private boolean responseError(HttpServletRequest request, Resp resp, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setContentType(CommonConstant.ContentType_JSON);
			response.setCharacterEncoding(CommonConstant.CharacterEncoding_Default);
			out = response.getWriter();
			if (out == null) {
				logger.error("error is output failed,out==null.");
				return false;
			}
			String finalResp = null;
			finalResp = StringUtils.toJSONString(resp);
			if (StringUtils.isEmpty(finalResp)) {
				logger.error("error is output failed,finalResp is empty.");
				return false;
			}
			out.print(finalResp);
			response = null;
		} catch (Exception e) {
			logger.error("The server appeared abnormal,responseError.", e);
			return false;
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
			out = null;
		}
		return true;
	}
}
