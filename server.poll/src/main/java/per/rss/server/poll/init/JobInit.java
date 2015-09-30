package per.rss.server.poll.init;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.rss.core.base.util.UUIDUtils;
import per.rss.core.job.distributed.elastic.ElasticJobConfig;
import per.rss.core.job.distributed.elastic.ElasticJobUtils;
import per.rss.server.poll.biz.feed.sync.impl.FeedSyncBizImpl;
import per.rss.server.poll.bo.feed.FeedSyncBo;
import per.rss.server.poll.dao.job.ScheduleJobDao;
import per.rss.server.poll.model.job.ScheduleJob;
import per.rss.server.poll.util.SpringUtil;

@Component
public class JobInit {
	private static final Logger logger = LoggerFactory.getLogger(JobInit.class);

	@Autowired
	private ScheduleJobDao scheduleJobDao;

	@Autowired
	private ElasticJobUtils elasticJobUtils;

	public void lazyInit() throws InterruptedException {
		if (SpringUtil.getApplicationContext() == null) {
			Thread.sleep(2 * 1000);
			lazyInit();
		}
	}

	public void initFeedSyncJobs() {
		try {
			// lazyInit();
			// 从数据库load所有任务
			List<ScheduleJob> jobList = scheduleJobDao.findByJobInit();

			// 将任务加入到分布式任务队列
			ElasticJobConfig<FeedSyncBo> j1 = new ElasticJobConfig<FeedSyncBo>();
			j1.setId(UUIDUtils.randomUUID());
			j1.setName("FeedSyncJobs_" + j1.getId());
			j1.setCronExpression("0/5 * * * * ?");
			FeedSyncBo feedSyncBo = new FeedSyncBo();
			feedSyncBo.setId(UUIDUtils.randomUUID());
			feedSyncBo.setLink("http://hanhanone.sinaapp.com/feed/dajia");
			feedSyncBo.setLastedSyncDateTime(new Date().getTime() - 2 * 24 * 60 * 60 * 1000);// 2天前
			// logger.debug("LastedSyncDate：" +
			// feedSyncBo.getLastedSyncDateTime());
			j1.setParam(feedSyncBo);
			j1.setTargetClass(FeedSyncBizImpl.class);

			ElasticJobConfig<FeedSyncBo> j2 = new ElasticJobConfig<FeedSyncBo>();
			j2.setId(UUIDUtils.randomUUID());
			j2.setName("FeedSyncJobs_" + j2.getId());
			j2.setCronExpression("0/8 * * * * ?");
			FeedSyncBo feedSyncBo2 = new FeedSyncBo();
			feedSyncBo2.setId(UUIDUtils.randomUUID());
			feedSyncBo2.setLink("http://sinacn.weibodangan.com/user/1850988623/rss/");
			feedSyncBo2.setLastedSyncDateTime(new Date().getTime() - 24 * 60 * 60 * 1000);// 昨天的现在
			j2.setParam(feedSyncBo2);
			j2.setTargetClass(FeedSyncBizImpl.class);

			elasticJobUtils.initJobOne(j1);
			// elasticJobUtils.initJobOne(j2);
		} catch (Throwable e) {
			logger.error("JobInit is error.", e);
			System.exit(-1);
		}
	}
	
	public void destory(){  
    	logger.debug("Destory is complete.");
    }
}
