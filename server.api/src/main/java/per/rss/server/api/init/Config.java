package per.rss.server.api.init;

import org.springframework.beans.factory.annotation.Autowired;

public class Config {
	public static String portal_webpage_domain;

	@Autowired(required = true)
	public void setPortal_webpage_domain(String portal_webpage_domain) {
		Config.portal_webpage_domain = portal_webpage_domain;
	}
}
