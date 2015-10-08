package per.rss.server.poll.init;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.rss.core.base.constant.FeedSyncConstant;
import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.StringUtils;
import per.rss.core.job.distributed.elastic.ElasticJobConfig;
import per.rss.core.job.distributed.elastic.ElasticJobUtils;
import per.rss.server.poll.biz.feed.sync.impl.FeedSyncBizImpl;
import per.rss.server.poll.bo.feed.FeedSyncBo;
import per.rss.server.poll.dao.feed.FeedDao;
import per.rss.server.poll.model.feed.Feed;

@Component
public class JobInit {
	private static final Logger logger = LoggerFactory.getLogger(JobInit.class);

	@Autowired
	private FeedDao feedDao;

	@Autowired
	private ElasticJobUtils elasticJobUtils;

	public void initFeedSyncJobs() {
		try {
			// 从数据库load所有任务
			List<Feed> jobList = feedDao.findByLoadAllJob();
			if (CollectionUtils.isEmpty(jobList)) {
				logger.warn("在数据库中任务查询为空。");
				return;
			} else {
				logger.debug("数据库中任务数量为：" + jobList.size());
			}
			for (int i = 0; i < jobList.size(); i++) {
				boolean addResult = false;
				Feed feed = jobList.get(i);
				addResult = this.addOneFeedSyncJob(feed);
				if (!addResult) {
					logger.error("任务加载失败." + StringUtils.toJSONString(feed));
					continue;
				}
			}
		} catch (Throwable e) {
			logger.error("JobInit is error.", e);
			System.exit(-1);
		}
	}

	private boolean addOneFeedSyncJob(Feed feed) {
		FeedSyncBo feedSyncBo = new FeedSyncBo();
		feedSyncBo.setId(feed.getId());
		feedSyncBo.setUrl(feed.getUrl());
		ElasticJobConfig<FeedSyncBo> j1 = new ElasticJobConfig<FeedSyncBo>();
		j1.setId(feedSyncBo.getId());
		j1.setName("FeedSyncJobs_" + j1.getId());
		j1.setCronExpression(FeedSyncConstant.getValueByCode(feed.getSyncSpeed()));
		j1.setParam(feedSyncBo);
		j1.setTargetClass(FeedSyncBizImpl.class);

		// 将任务加入到分布式任务队列
		elasticJobUtils.initJobOne(j1);
		return true;
	}

	public void destory() {
		logger.debug("Destory is complete.");
	}
}
