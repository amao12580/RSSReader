package per.rss.server.poll.dao.feed.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.constant.FeedSyncConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.core.nosql.constant.DBFeedKeysConstant;
import per.rss.core.nosql.mongo.MongoBaseDao;
import per.rss.server.poll.dao.feed.FeedDao;
import per.rss.server.poll.model.feed.Feed;
import per.rss.server.poll.model.feed.piece.Create;

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
		update.set(DBFeedKeysConstant.lastedSyncStatus, feed.getLastedSyncStatus());
		update.set(DBFeedKeysConstant.lastedSyncDate, feed.getLastedSyncDate());
		return this.mongoBaseDao.updateFirst(query, update, Feed.class);
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

	@Override
	public List<Feed> findByLoadAllJob() {
		Feed feed = new Feed();
		feed.setId("e8562ab8bf6242cbad00989c4a93426b");
		feed.setUrl("http://hanhanone.sinaapp.com/feed/dajia");
		feed.setSyncSpeed(FeedSyncConstant.speed.medium.getCode());
		Create create = new Create();
		create.setDate(new Date());
		create.setIp("192.168.0.82");
		create.setUserId("10010");
		feed.setCreate(create);
		// this.mongoBaseDao.insert(feed);

		Set<String> queryFileds = new HashSet<String>(3);
		queryFileds.add(DBFeedKeysConstant.id);
		queryFileds.add(DBFeedKeysConstant.url);
		queryFileds.add(DBFeedKeysConstant.syncSpeed);

		Query query = new Query();
		query.addCriteria(new Criteria(DBFeedKeysConstant.id).ne(CommonConstant.stringBegining));
		query.addCriteria(new Criteria(DBFeedKeysConstant.url).ne(CommonConstant.stringBegining));
		query.addCriteria(new Criteria(DBFeedKeysConstant.lastedSyncDate).ne(CommonConstant.stringBegining));
		query.addCriteria(new Criteria(DBFeedKeysConstant.syncSpeed).ne(CommonConstant.stringBegining));
		// query.addCriteria(new Criteria(DBFeedKeysConstant.id).ne(null));
		// query.addCriteria(new Criteria(DBFeedKeysConstant.url).ne(null));
		// query.addCriteria(new
		// Criteria(DBFeedKeysConstant.lastedSyncDate).ne(null));
		// query.addCriteria(new
		// Criteria(DBFeedKeysConstant.syncSpeed).ne(null));
		return this.mongoBaseDao.find(queryFileds, query, Feed.class);
	}

	@Override
	public Date findByExcuteOneJob(String feedId) {
		Set<String> queryFileds = new HashSet<String>(1);
		queryFileds.add(DBFeedKeysConstant.lastedSyncDate);
		Query query = new Query();
		query.addCriteria(new Criteria(DBFeedKeysConstant.id).is(feedId));
		Feed feed = mongoBaseDao.findOne(queryFileds, query, Feed.class);
		if (feed == null) {
			return null;
		} else {
			return feed.getLastedSyncDate();
		}
	}

}
