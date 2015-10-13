package per.rss.server.api.filter.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import per.rss.core.base.util.UUIDUtils;

public final class CSRFTokenManager {

	static final String CSRF_PARAM_NAME = "CSRFToken";

	public static final String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenManager.class.getName() + ".tokenval";

	public static String getTokenForSession(HttpSession session) {
		String token = null;
		synchronized (session.getClass()) {
			token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
			if (null == token) {
				token = UUIDUtils.randomUUID();
				session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
			}
		}
		return token;
	}

	public static String getTokenFromRequest(HttpServletRequest request) {
		return request.getParameter(CSRF_PARAM_NAME);
	}

	private CSRFTokenManager() {
	};
}
