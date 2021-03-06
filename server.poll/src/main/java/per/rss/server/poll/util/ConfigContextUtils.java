package per.rss.server.poll.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 启动配置
 *
 */
@Component
public class ConfigContextUtils {
	private static final Logger logger = LoggerFactory.getLogger(ConfigContextUtils.class);

	public static ApplicationContext context = null;

	public static void init() {
		try {
			logger.info("Start service...");
			// 初始化spring
			context = new ClassPathXmlApplicationContext("conf/init-server.poll.xml");
			if(context!=null){
				SpringUtil.setApplicationContext(context);
			}
			assert context == null;
			logger.info("Start completion.");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.exit(-1);
		}
	}

}
