package per.rss.server.poll.biz.feed.sync.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.biz.feed.sync.FeedSyncBiz;
import per.rss.server.poll.bo.feed.FeedSyncBo;
import per.rss.server.poll.model.log.LogFeedSync;
import per.rss.server.poll.util.RSSFetcherUtils;
import per.rss.server.poll.util.job.elastic.BaseElasticJob;

@Service(value = "feedSyncBiz")
public class FeedSyncBizImpl extends BaseElasticJob implements FeedSyncBiz {
	protected static transient Logger log = LoggerFactory.getLogger(FeedSyncBizImpl.class);

	@Override
	public FeedSyncBo doFeedSync(FeedSyncBo feedSyncBo) {
		LogFeedSync logFeedSync=RSSFetcherUtils.doFetch(feedSyncBo.getLink());
		if(logFeedSync==null){
			return null;
		}
		feedSyncBo.setLogFeedSync(logFeedSync);
		return feedSyncBo;
	}

	@Override
	public FeedSyncBo excuteingJob(String jobParam) {
		FeedSyncBo feedSyncBo = StringUtils.formatJson(jobParam, FeedSyncBo.class);
		if(feedSyncBo==null){
			return null;
		}
		if(StringUtils.isEmpty(feedSyncBo.getLink())){
			return null;
		}
		if(StringUtils.isEmpty(feedSyncBo.getId())){
			return null;
		}
		return doFeedSync(feedSyncBo);
	}

	@Override
	public boolean excuteingResult(Object result) {
		return true;
	}

}
