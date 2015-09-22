package per.rss.server.poll.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import per.rss.core.base.task.BaseTask;
import per.rss.server.poll.biz.feed.sync.FeedSyncBiz;

@Component
public class FeedSyncTask extends BaseTask {
	@Autowired
	FeedSyncBiz feedSyncBiz;

	/**
	 * 定时一小时执行一次
	 */
	@Scheduled(cron = "0 0 */1 * * *")
	public void buildOnTimer() {
		// feedSyncBiz.doTimingSync(feedId, URL, lastedSyncDate);();
	}

	@Scheduled(cron = "*/10 * * * * *")
	public void s10() {
		System.out.println("10s");
	}

	@Scheduled(cron = "0 */1 * * * *")
	public void m1() {
		System.out.println("1m");
	}

	@Scheduled(cron = "0 0 */1 * * *")
	public void h1() {
		System.out.println("1h");
	}
}
