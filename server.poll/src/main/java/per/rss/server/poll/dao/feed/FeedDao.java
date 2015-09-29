package per.rss.server.poll.dao.feed;

import per.rss.server.poll.model.feed.Feed;

/**
 * @author cifpay
 */
public interface FeedDao {
	public int updateByFeedSync(Feed feed);

	public Feed findById(String feedId);

	public boolean findExistById(String feedId);
}
