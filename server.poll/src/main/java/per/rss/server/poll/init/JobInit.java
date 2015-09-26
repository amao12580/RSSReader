package per.rss.server.poll.init;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.rss.core.base.util.UUIDUtils;
import per.rss.server.poll.biz.feed.sync.impl.FeedSyncBizImpl;
import per.rss.server.poll.bo.feed.FeedSyncBo;
import per.rss.server.poll.util.job.elastic.ElasticJobConfig;
import per.rss.server.poll.util.job.elastic.ElasticJobUtils;

@Component
public class JobInit {
	private static final Logger logger = LoggerFactory.getLogger(JobInit.class);

//	@Autowired
//	private ScheduleJobDao scheduleJobDao;
	
	@Autowired
	private ElasticJobUtils jobUtils;

	 @PostConstruct
	 public void initFeedSyncJobs() {
		 try{
			 ElasticJobConfig<FeedSyncBo> j1=new ElasticJobConfig<FeedSyncBo>();
			 j1.setId(UUIDUtils.randomUUID());
			 j1.setName("FeedSyncJobs");
			 j1.setCronExpression("0/5 * * * * ?");
			 FeedSyncBo feedSyncBo=new FeedSyncBo();
			 feedSyncBo.setId(UUIDUtils.randomUUID());
			 feedSyncBo.setLink("http://hanhanone.sinaapp.com/feed/dajia");
			 feedSyncBo.setLastedSyncDate(new Date());
			 j1.setParam(feedSyncBo);
			 j1.setTargetClass(FeedSyncBizImpl.class);
			 jobUtils.initJobOne(j1);
		 }catch(Throwable e){
			 logger.error("JobInit is error.",e);
			 System.exit(-1);
		 }
	 }
	 @PreDestroy
	public void destory() {
		logger.debug("Systeam job information destory is complete.");
	}
}
