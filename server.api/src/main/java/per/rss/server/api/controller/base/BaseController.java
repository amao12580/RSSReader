package per.rss.server.api.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.server.api.filter.csrf.CSRFTokenManager;

public class BaseController {
	// private static final Logger logger =
	// LoggerFactory.getLogger(BaseController.class);

	public String toCallBackResponse(String callBackFunctionName, Object params) {
		return callBackFunctionName + "(" + StringUtils.toJSONString(params) + ")";
	}

	protected boolean isValidCsrfHeaderToken(HttpSession session, HttpServletRequest request) {
		String reqToken = request.getParameter(CommonConstant.CSRFTOKENNAME);
		// String reqToken2 = request.getHeader(CommonConstant.CSRFTOKENNAME);
		// String reqToken3 = request.getContentType();
		Object sessionToken = session.getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
		// logger.debug("reqToken2:" + reqToken2);
		// logger.debug("reqToken3:" + reqToken3);
		if (StringUtils.isEmpty(reqToken)) {
			return false;
		}
		// logger.debug("reqToken:" + reqToken);
		if (sessionToken == null) {
			return false;
		}
		// logger.debug("sessionToken:" + sessionToken.toString());

		if (!reqToken.equals(sessionToken.toString())) {
			return false;
		}
		return true;
	}
}
