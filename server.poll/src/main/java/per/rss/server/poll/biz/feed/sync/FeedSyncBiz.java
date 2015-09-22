package per.rss.server.poll.biz.feed.sync;

import java.util.Date;

public interface FeedSyncBiz {
	public void doTimingSync(String feedId,String URL,Date lastedSyncDate);
}
