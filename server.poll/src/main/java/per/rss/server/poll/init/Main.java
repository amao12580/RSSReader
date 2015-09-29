package per.rss.server.poll.init;

import per.rss.server.poll.util.ConfigContextUtils;

public class Main {
	static {
		System.setProperty("user.timezone", "Asia/Shanghai");
	}

	public static void main(String[] args) {
		ConfigContextUtils.init();
	}
}
