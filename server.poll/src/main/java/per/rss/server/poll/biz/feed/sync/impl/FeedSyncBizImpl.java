package per.rss.server.poll.biz.feed.sync.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import per.rss.server.poll.biz.feed.sync.FeedSyncBiz;

@Service(value = "feedSyncBiz")
public class FeedSyncBizImpl implements FeedSyncBiz {

	@Override
	public void doTimingSync(String feedId, String URL, Date lastedSyncDate) {
		// TODO Auto-generated method stub

	}

}
