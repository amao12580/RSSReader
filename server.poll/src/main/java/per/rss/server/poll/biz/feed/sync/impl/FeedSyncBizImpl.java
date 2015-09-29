package per.rss.server.poll.biz.feed.sync.impl;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.ObjectUtil;
import per.rss.core.base.util.StringUtils;
import per.rss.core.job.distributed.elastic.BaseOneOffElasticJob;
import per.rss.server.poll.biz.feed.sync.FeedSyncBiz;
import per.rss.server.poll.bo.feed.FeedParseBo;
import per.rss.server.poll.bo.feed.FeedSyncBo;
import per.rss.server.poll.dao.article.ArticleDao;
import per.rss.server.poll.dao.feed.FeedDao;
import per.rss.server.poll.model.feed.Article;
import per.rss.server.poll.model.feed.Feed;
import per.rss.server.poll.model.log.LogFeedParser;
import per.rss.server.poll.model.log.LogFeedSync;
import per.rss.server.poll.util.RSSFetcherUtils;

@Service(value = "feedSyncBiz")
public class FeedSyncBizImpl extends BaseOneOffElasticJob<FeedSyncBo>implements FeedSyncBiz {
	private static final Logger logger = LoggerFactory.getLogger(FeedSyncBizImpl.class);

	@Autowired(required = true)
	private ArticleDao articleDao;

	@Autowired(required = true)
	private FeedDao feedDao;

	@Override
	public FeedSyncBo doFeedSync(FeedSyncBo feedSyncBo) {
		Long lastedSyncDateTime = feedSyncBo.getLastedSyncDateTime();
		Date lastedSyncDate = null;
		if (lastedSyncDateTime != null && (lastedSyncDateTime > 0l)) {
			lastedSyncDate = new Date(lastedSyncDateTime);
		}
		LogFeedSync logFeedSync = RSSFetcherUtils.doFetch(feedSyncBo.getId(), feedSyncBo.getLink(), lastedSyncDate);
		if (logFeedSync == null) {
			return null;
		}
		feedSyncBo.setLogFeedSync(logFeedSync);
		return feedSyncBo;
	}

	@Override
	public FeedSyncBo excuteingJob(String jobParam) {
		// logger.debug("jobParam:" + jobParam);
		FeedSyncBo feedSyncBo = StringUtils.formatJson(jobParam, FeedSyncBo.class);
		if (feedSyncBo == null) {
			return null;
		}
		if (StringUtils.isEmpty(feedSyncBo.getLink())) {
			return null;
		}
		if (StringUtils.isEmpty(feedSyncBo.getId())) {
			return null;
		}
		return doFeedSync(feedSyncBo);
	}

	@Override
	public boolean excuteingResult(FeedSyncBo feedSyncBo) {
		if (feedSyncBo == null) {
			return false;
		}
		// 1.同步：同时在本任务中更新任务参数：lastedSyncDate

		// 2.同步：记录所有文章 ok
		Set<Article> articles = this.getArticleNews(feedSyncBo);
		if (articles == null) {
			return false;
		}
		if (CollectionUtils.isEmpty(articles)) {
			return true;
		}
		int articleSize = articles.size();
		logger.debug("articles size is :" + articleSize);
		if (articleSize > 0) {
			articleDao.insertNews(articles);
		} else {
			articleSize = 0;
		}
		// 3.同步：更新订阅源的最后一次更新时间、状态。
		Feed feed = this.getFeedByFeedSync(feedSyncBo, articleSize);
		if (feed != null) {
			int update_ret = feedDao.updateByFeedSync(feed);
			if (update_ret < 0) {
				return false;
			}
		}

		// 4.异步：记录本次更新日志，抓取日志、解析日志

		// 5.异步：MQ请求为用户更新订阅，考虑用户自定义过滤规则、用户自定义更新事件处理

		return true;
	}

	private Feed getFeedByFeedSync(FeedSyncBo feedSyncBo, int articleSize) {
		LogFeedSync logFeedSync = feedSyncBo.getLogFeedSync();
		if (logFeedSync == null) {
			return null;
		}
		LogFeedParser logFeedParser = logFeedSync.getLogFeedParser();
		if (logFeedParser == null) {
			return null;
		}
		FeedParseBo feedParseBo = logFeedParser.getFeedParseBo();
		if (feedParseBo == null) {
			return null;
		}
		Feed feed = ObjectUtil.copyProperties(feedParseBo, Feed.class);
		if (feed == null) {
			return null;
		}
		feed.setLastedSyncArticleSum(articleSize);
		return null;
	}

	private Set<Article> getArticleNews(FeedSyncBo feedSyncBo) {
		LogFeedSync logFeedSync = feedSyncBo.getLogFeedSync();
		if (logFeedSync == null) {
			return null;
		}
		LogFeedParser logFeedParser = logFeedSync.getLogFeedParser();
		if (logFeedParser == null) {
			return null;
		}
		FeedParseBo feedParseBo = logFeedParser.getFeedParseBo();
		if (feedParseBo == null) {
			return null;
		}
		return feedParseBo.getItem();
	}
}
