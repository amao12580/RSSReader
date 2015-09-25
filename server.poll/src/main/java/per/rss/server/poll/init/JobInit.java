package per.rss.server.poll.init;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.rss.core.base.util.UUIDUtils;
import per.rss.server.poll.model.feed.Feed;
import per.rss.server.poll.util.job.elastic.JobConfig;
import per.rss.server.poll.util.job.elastic.JobUtils;

@Component
public class JobInit {
	private static final Logger logger = LoggerFactory.getLogger(JobInit.class);

//	@Autowired
//	private ScheduleJobDao scheduleJobDao;
	
	@Autowired
	private JobUtils jobUtils;

	 @PostConstruct
	 public void init() {
		 try{
			 JobConfig<Feed> j1=new JobConfig<Feed>();
			 j1.setId(UUIDUtils.randomUUID());
			 j1.setName("oneOffElasticDemoJobTest");
			 j1.setCronExpression("0/10 * * * * ?");
			 Feed feed=new Feed();
			 feed.setId(UUIDUtils.randomUUID());
			 feed.setLink("http://hanhanone.sinaapp.com/feed/dajia");
			 j1.setParam(feed);
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
