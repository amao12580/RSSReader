package per.rss.server.api.filter.cookie;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import per.rss.core.base.util.StringUtils;

public class CookieFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Cookie[] cookies = req.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie == null) {
					continue;
				}
				String cookieName = cookie.getName();
				if (StringUtils.isEmpty(cookieName)) {
					continue;
				}
				// if
				// (!CommonConstant.DEFAULTCOOIKENAME.equalsIgnoreCase(cookieName))
				// {
				if (!cookie.getSecure()) {
					cookie.setMaxAge(0);
				}
				cookie.setHttpOnly(true);
				// cookie.setSecure(true);
				resp.addCookie(cookie);
				// }
			}
		}
		chain.doFilter(req, resp);
	}

	public void destroy() {
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
