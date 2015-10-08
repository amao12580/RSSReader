package per.rss.server.poll.dao.feed;

import java.util.Date;
import java.util.List;

import per.rss.server.poll.model.feed.Feed;

public interface FeedDao {
	public int updateByFeedSync(Feed feed);

	public boolean findExistById(String feedId);

	public List<Feed> findByLoadAllJob();

	public Date findByExcuteOneJob(String feedId);
}
