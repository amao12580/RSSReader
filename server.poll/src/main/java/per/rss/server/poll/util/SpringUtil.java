package per.rss.server.poll.util;

import org.springframework.context.ApplicationContext;

public class SpringUtil {

	private static ApplicationContext applicationContext;

	private SpringUtil() {
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		if (applicationContext != null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static Object getBean(String name, Class<?> cls) {
		return applicationContext.getBean(name, cls);
	}
}
