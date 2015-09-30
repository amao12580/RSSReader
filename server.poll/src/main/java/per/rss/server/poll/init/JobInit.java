package per.rss.server.poll.init;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.rss.core.base.constant.FeedSyncConstant;
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

	public void initFeedSyncJobs() {
		try {
			// 从数据库load所有任务
			List<ScheduleJob> jobList = scheduleJobDao.findByJobInit();

			// 将任务加入到分布式任务队列
			FeedSyncBo feedSyncBo = new FeedSyncBo();
			feedSyncBo.setId("e8562ab8bf6242cbad00989c4a93426b");
			feedSyncBo.setLink("http://hanhanone.sinaapp.com/feed/dajia");
			feedSyncBo.setLastedSyncDateTime(new Date().getTime() - 2 * 24 * 60 * 60 * 1000);// 2天前
			feedSyncBo.setSyncSpeed(FeedSyncConstant.speed.fastest.getCode());
			
			ElasticJobConfig<FeedSyncBo> j1 = new ElasticJobConfig<FeedSyncBo>();
			j1.setId(feedSyncBo.getId());
			j1.setName("FeedSyncJobs_" + j1.getId());
			j1.setCronExpression(FeedSyncConstant.getValueByCode(feedSyncBo.getSyncSpeed()));
			j1.setParam(feedSyncBo);
			j1.setTargetClass(FeedSyncBizImpl.class);
			
			elasticJobUtils.initJobOne(j1);
		} catch (Throwable e) {
			logger.error("JobInit is error.", e);
			System.exit(-1);
		}
	}
	
	public void destory(){  
    	logger.debug("Destory is complete.");
    }
}
