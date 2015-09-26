package per.rss.server.poll.biz.feed.sync;

import per.rss.server.poll.bo.feed.FeedSyncBo;

public interface FeedSyncBiz {
	public FeedSyncBo doFeedSync(FeedSyncBo feedSyncBo);
}
