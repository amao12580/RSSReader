package per.rss.server.poll.dao.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import per.rss.core.nosql.mongo.MongoBaseDao;
import per.rss.server.poll.dao.log.LogFeedFetcherDao;
import per.rss.server.poll.model.log.LogFeedFetcher;

@Repository(value = "logFeedFetcherDao")
public class LogFeedFetcherDaoImpl implements LogFeedFetcherDao {

	@Autowired(required = true)
	private MongoBaseDao<LogFeedFetcher> mongoBaseDao;

	@Override
	public void insertOne(LogFeedFetcher logFeedFetcher) {
		this.mongoBaseDao.insert(logFeedFetcher);
	}

}
