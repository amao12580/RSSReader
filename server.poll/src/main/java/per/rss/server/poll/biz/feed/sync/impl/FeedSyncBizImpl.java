package per.rss.server.poll.biz.feed.sync.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.StringUtils;
import per.rss.core.job.distributed.elastic.BaseOneOffElasticJob;
import per.rss.server.poll.biz.feed.sync.FeedSyncBiz;
import per.rss.server.poll.bo.feed.FeedSyncBo;
import per.rss.server.poll.dao.article.ArticleDao;
import per.rss.server.poll.model.feed.Article;
import per.rss.server.poll.model.feed.Feed;
import per.rss.server.poll.model.log.LogFeedParser;
import per.rss.server.poll.model.log.LogFeedSync;
import per.rss.server.poll.util.RSSFetcherUtils;

@Service(value = "feedSyncBiz")
public class FeedSyncBizImpl extends BaseOneOffElasticJob<FeedSyncBo> implements FeedSyncBiz {
	@Autowired(required = true)
	private ArticleDao articleDao;

	@Override
	public FeedSyncBo doFeedSync(FeedSyncBo feedSyncBo) {
		LogFeedSync logFeedSync = RSSFetcherUtils.doFetch(feedSyncBo.getId(), feedSyncBo.getLink(),
				feedSyncBo.getLastedSyncDate());
		if (logFeedSync == null) {
			return null;
		}
		feedSyncBo.setLogFeedSync(logFeedSync);
		return feedSyncBo;
	}

	@Override
	public FeedSyncBo excuteingJob(String jobParam) {
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
		// 1.同步：记录所有文章  ok
		Set<Article> articles=this.getArticleNews(feedSyncBo);
		if(articles==null){
			return false;
		}
		if(CollectionUtils.isEmpty(articles)){
			return true;
		}
		articleDao.insertNews(articles);
		// 2.同步：更新订阅源的最后一次更新时间、状态

		// 3.异步：记录本次更新日志，抓取日志、解析日志
		
		// 4.异步：MQ请求为用户更新订阅，考虑用户自定义过滤规则、用户自定义更新事件处理

		return true;
	}

	private Set<Article> getArticleNews(FeedSyncBo feedSyncBo) {
		LogFeedSync logFeedSync=feedSyncBo.getLogFeedSync();
		if(logFeedSync==null){
			return null;
		}
		LogFeedParser logFeedParser=logFeedSync.getLogFeedParser();
		if(logFeedParser==null){
			return null;
		}
		Feed feed=logFeedParser.getFeed();
		if(feed==null){
			return null;
		}
		return feed.getItem();
	}
}
