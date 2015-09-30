package per.rss.server.poll.dao.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import per.rss.core.nosql.mongo.MongoBaseDao;
import per.rss.server.poll.dao.log.LogFeedParserDao;
import per.rss.server.poll.model.log.LogFeedParser;

@Repository(value = "logFeedParserDao")
public class LogFeedParserDaoImpl implements LogFeedParserDao {

	@Autowired(required = true)
	private MongoBaseDao<LogFeedParser> mongoBaseDao;

	@Override
	public void insertOne(LogFeedParser logFeedParser) {
		this.mongoBaseDao.insert(logFeedParser);
	}

}
