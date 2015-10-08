package per.rss.server.poll.biz.feed.sync.impl;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import per.rss.core.base.bo.log.LogFeedFetcherBo;
import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.DateTimeUtils;
import per.rss.core.base.util.ObjectUtil;
import per.rss.core.base.util.StringUtils;
import per.rss.core.job.distributed.elastic.BaseOneOffElasticJob;
import per.rss.server.poll.biz.feed.sync.FeedSyncBiz;
import per.rss.server.poll.bo.feed.FeedParseBo;
import per.rss.server.poll.bo.feed.FeedSyncBo;
import per.rss.server.poll.bo.feed.LogFeedParserBo;
import per.rss.server.poll.bo.feed.LogFeedSyncBo;
import per.rss.server.poll.dao.article.ArticleDao;
import per.rss.server.poll.dao.feed.FeedDao;
import per.rss.server.poll.dao.log.LogFeedFetcherDao;
import per.rss.server.poll.dao.log.LogFeedParserDao;
import per.rss.server.poll.model.feed.Feed;
import per.rss.server.poll.model.feed.piece.Article;
import per.rss.server.poll.model.log.LogFeedFetcher;
import per.rss.server.poll.model.log.LogFeedParser;
import per.rss.server.poll.util.RSSFetcherUtils;
import per.rss.server.poll.util.SpringUtil;

public class FeedSyncBizImpl extends BaseOneOffElasticJob<FeedSyncBo>implements FeedSyncBiz {
	private static final Logger logger = LoggerFactory.getLogger(FeedSyncBizImpl.class);

	private static ArticleDao articleDao = null;

	private static FeedDao feedDao = null;

	private static LogFeedFetcherDao logFeedFetcherDao = null;

	private static LogFeedParserDao logFeedParserDao = null;

	private static ThreadPoolTaskExecutor logRecordExecutor = null;

	static {
		if (articleDao == null) {
			articleDao = (ArticleDao) SpringUtil.getBean("articleDao");
		}
		if (feedDao == null) {
			feedDao = (FeedDao) SpringUtil.getBean("feedDao");
		}
		if (logFeedFetcherDao == null) {
			logFeedFetcherDao = (LogFeedFetcherDao) SpringUtil.getBean("logFeedFetcherDao");
		}
		if (logFeedParserDao == null) {
			logFeedParserDao = (LogFeedParserDao) SpringUtil.getBean("logFeedParserDao");
		}
		if (logRecordExecutor == null) {
			logRecordExecutor = (ThreadPoolTaskExecutor) SpringUtil.getBean("logRecordExecutor");
		}
	}

	@Override
	public FeedSyncBo doFeedSync(FeedSyncBo feedSyncBo) {
		Date lastedSyncDate = null;
		lastedSyncDate = feedDao.findByExcuteOneJob(feedSyncBo.getId());
		if (!DateTimeUtils.isValid(lastedSyncDate)) {
			lastedSyncDate = null;
		}
		LogFeedSyncBo logFeedSync = RSSFetcherUtils.doFetch(feedSyncBo.getId(), feedSyncBo.getUrl(), lastedSyncDate);
		if (logFeedSync == null) {
			return null;
		}
		feedSyncBo.setLogFeedSync(logFeedSync);
		return feedSyncBo;
	}

	@Override
	public FeedSyncBo excuteingJob(String jobParam) {
		logger.debug("jobParam:" + jobParam);
		FeedSyncBo feedSyncBo = StringUtils.formatJson(jobParam, FeedSyncBo.class);
		if (feedSyncBo == null) {
			return null;
		}
		if (StringUtils.isEmpty(feedSyncBo.getUrl())) {
			return null;
		}
		if (StringUtils.isEmpty(feedSyncBo.getId())) {
			return null;
		}
		return doFeedSync(feedSyncBo);
	}

	@Override
	public boolean excuteingResult(final FeedSyncBo feedSyncBo) {
		if (feedSyncBo == null) {
			return false;
		}
		// 1.同步：记录所有文章 ok
		boolean insertArticles = false;
		insertArticles = this.insertArticles(feedSyncBo);
		logger.debug("insertArticles result is :" + insertArticles);
		// 2.1同步：更新订阅源的最后一次更新时间、状态。 ok
		// 2.2同步：同时在本任务中更新任务参数：lastedSyncDate，api不支持，使用DB实现
		boolean updateFeedSync = false;
		updateFeedSync = this.updateFeedSync(feedSyncBo, insertArticles);
		logger.debug("updateFeedSync result is :" + updateFeedSync);
		// 3.异步：记录本次更新日志，抓取日志、解析日志 ok
		logRecordExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 记录日志
				LogFeedFetcher logFeedFetcher = getErrorLogFeedFetcher(feedSyncBo);
				if (logFeedFetcher != null) {
					logFeedFetcherDao.insertOne(logFeedFetcher);
				}
			}
		});
		logRecordExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 记录日志
				LogFeedParser logFeedParser = getErrorLogFeedParser(feedSyncBo);
				if (logFeedParser != null) {
					logFeedParserDao.insertOne(logFeedParser);
				}
			}
		});

		// 4.异步：MQ请求为用户更新订阅，考虑用户自定义过滤规则、用户自定义更新事件处理

		if (insertArticles && updateFeedSync) {
			return true;
		}
		return false;
	}

	private boolean updateFeedSync(FeedSyncBo feedSyncBo, boolean insertArticles) {
		int articleSize = -1;
		Set<Article> articles = this.getArticleNews(feedSyncBo);
		if (articles != null) {
			articleSize = articles.size();
		}
		if (articleSize < 0) {
			articleSize = 0;
		}
		Feed feed = this.getFeedByFeedSync(feedSyncBo, articleSize);
		if (!insertArticles) {
			if (articleSize <= 0) {
				feed.setLastedSyncStatus(CommonConstant.status.failed.getCode());
			}
		}
		if (feed != null) {
			int update_ret = feedDao.updateByFeedSync(feed);
			if (update_ret < 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean insertArticles(FeedSyncBo feedSyncBo) {
		Set<Article> articles = this.getArticleNews(feedSyncBo);
		if (articles == null) {
			return false;
		}
		int articleSize = articles.size();
		logger.debug("articles size is :" + articleSize);
		if (articleSize > 0) {
			articleDao.insertNews(articles);
		}
		return true;
	}

	private LogFeedParser getErrorLogFeedParser(FeedSyncBo feedSyncBo) {
		LogFeedSyncBo logFeedSync = feedSyncBo.getLogFeedSync();
		if (logFeedSync == null) {
			return null;
		}
		LogFeedParserBo logFeedParserBo = logFeedSync.getLogFeedParserBo();
		if (logFeedParserBo == null) {
			return null;
		}

		Integer requestStatus = logFeedParserBo.getStatus();
		if (requestStatus == null) {
			return null;
		}
		if (requestStatus.equals(CommonConstant.status.success.getCode())) {
			return null;
		}

		LogFeedParser logFeedParser = ObjectUtil.copyProperties(logFeedParserBo, LogFeedParser.class);
		if (logFeedParser == null) {
			return null;
		}
		logFeedParser.setFeedId(logFeedSync.getFeedId());
		return logFeedParser;
	}

	private LogFeedFetcherBo getLogFeedFetcherBo(FeedSyncBo feedSyncBo) {
		LogFeedSyncBo logFeedSync = feedSyncBo.getLogFeedSync();
		if (logFeedSync == null) {
			return null;
		}
		return logFeedSync.getLogFeedFetcherBo();
	}

	private FeedParseBo getFeedParseBo(FeedSyncBo feedSyncBo) {
		LogFeedSyncBo logFeedSync = feedSyncBo.getLogFeedSync();
		if (logFeedSync == null) {
			return null;
		}
		LogFeedParserBo logFeedParser = logFeedSync.getLogFeedParserBo();
		if (logFeedParser == null) {
			return null;
		}
		return logFeedParser.getFeedParseBo();
	}

	private LogFeedFetcher getErrorLogFeedFetcher(FeedSyncBo feedSyncBo) {
		LogFeedFetcherBo logFeedFetcherBo = getLogFeedFetcherBo(feedSyncBo);
		if (logFeedFetcherBo == null) {
			return null;
		}
		Integer requestStatus = logFeedFetcherBo.getRequestStatus();
		if (requestStatus == null) {
			return null;
		}
		if (requestStatus.equals(CommonConstant.status.success.getCode())) {
			return null;
		}

		Integer responseStatus = logFeedFetcherBo.getResponseStatus();
		if (responseStatus == null) {
			return null;
		}
		if (responseStatus.equals(CommonConstant.status.success.getCode())) {
			return null;
		}

		LogFeedFetcher logFeedFetcher = ObjectUtil.copyProperties(logFeedFetcherBo, LogFeedFetcher.class);
		if (logFeedFetcher == null) {
			return null;
		}
		logFeedFetcher.setFeedId(feedSyncBo.getId());
		return logFeedFetcher;
	}

	private Feed getFeedByFeedSync(FeedSyncBo feedSyncBo, int articleSize) {
		LogFeedSyncBo logFeedSync = feedSyncBo.getLogFeedSync();
		if (logFeedSync == null) {
			return null;
		}
		LogFeedParserBo logFeedParserBo = logFeedSync.getLogFeedParserBo();
		if (logFeedParserBo == null) {
			return null;
		}
		FeedParseBo feedParseBo = logFeedParserBo.getFeedParseBo();
		if (feedParseBo == null) {
			return null;
		}
		Feed feed = ObjectUtil.copyProperties(feedParseBo, Feed.class);
		if (feed == null) {
			return null;
		}
		feed.setLastedSyncStatus(CommonConstant.status.failed.getCode());
		LogFeedFetcherBo LogFeedFetcherBo = logFeedSync.getLogFeedFetcherBo();
		Integer RequestStatus = LogFeedFetcherBo.getRequestStatus();
		if (RequestStatus != null && RequestStatus.equals(CommonConstant.status.success.getCode())) {
			Integer ResponseStatus = LogFeedFetcherBo.getResponseStatus();
			if (ResponseStatus != null && ResponseStatus.equals(CommonConstant.status.success.getCode())) {
				Integer parseStatus = logFeedParserBo.getStatus();
				if (parseStatus != null && parseStatus.equals(CommonConstant.status.success.getCode())) {
					feed.setLastedSyncStatus(CommonConstant.status.success.getCode());
				}
			}
		}
		feed.setLastedSyncArticleSum(articleSize);
		return feed;
	}

	private Set<Article> getArticleNews(FeedSyncBo feedSyncBo) {
		FeedParseBo feedParseBo = getFeedParseBo(feedSyncBo);
		if (feedParseBo == null) {
			return null;
		}
		return feedParseBo.getItem();
	}
}
