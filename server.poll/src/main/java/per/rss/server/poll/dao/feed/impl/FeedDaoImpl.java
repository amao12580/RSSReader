package per.rss.server.poll.dao.feed.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import per.rss.core.base.util.StringUtils;
import per.rss.core.nosql.constant.DBFeedKeysConstant;
import per.rss.core.nosql.mongo.MongoBaseDao;
import per.rss.server.poll.dao.feed.FeedDao;
import per.rss.server.poll.model.feed.Feed;

@Repository(value = "feedDao")
public class FeedDaoImpl implements FeedDao {

	@Autowired(required = true)
	private MongoBaseDao<Feed> mongoBaseDao;

	@Override
	public int updateByFeedSync(Feed feed) {
		String feedId = feed.getId();
		if (!this.findExistById(feedId)) {
			this.mongoBaseDao.insert(feed);
			return 1;
		}
		Query query = new Query();
		query.addCriteria(new Criteria(DBFeedKeysConstant.id).is(feedId));
		Update update = new Update();
		update.set(DBFeedKeysConstant.title, feed.getTitle());
		update.set(DBFeedKeysConstant.image, feed.getImage());
		update.set(DBFeedKeysConstant.link, feed.getLink());
		update.set(DBFeedKeysConstant.description, feed.getDescription());
		update.set(DBFeedKeysConstant.language, feed.getLanguage());
		update.set(DBFeedKeysConstant.generator, feed.getGenerator());
		update.set(DBFeedKeysConstant.lastBuildDate, feed.getLastBuildDate());
		update.set(DBFeedKeysConstant.ttl, feed.getTtl());
		update.set(DBFeedKeysConstant.copyright, feed.getCopyright());
		update.set(DBFeedKeysConstant.pubDate, feed.getPubDate());
		update.set(DBFeedKeysConstant.category, feed.getCategory());
		update.set(DBFeedKeysConstant.lastedSyncArticleSum, feed.getLastedSyncArticleSum());
		update.set(DBFeedKeysConstant.lastedSyncDate, feed.getLastedSyncDate());
		update.set(DBFeedKeysConstant.feedCreate, feed.getFeedCreate());
		return this.mongoBaseDao.updateFirst(query, update, Feed.class);
	}

	@Override
	public Feed findById(String feedId) {
		if (StringUtils.isEmpty(feedId)) {
			return null;
		}
		Query query = new Query();
		query.addCriteria(new Criteria(DBFeedKeysConstant.id).is(feedId));
		return this.mongoBaseDao.findOne(query, Feed.class);
	}

	@Override
	public boolean findExistById(String feedId) {
		if (StringUtils.isEmpty(feedId)) {
			return false;
		}
		Query query = new Query();
		query.addCriteria(new Criteria(DBFeedKeysConstant.id).is(feedId));
		return this.mongoBaseDao.exists(query, Feed.class);
	}

}
