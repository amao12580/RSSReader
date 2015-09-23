package per.rss.server.poll.init;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import per.rss.server.poll.biz.job.ScheduleJobBiz;
import per.rss.server.poll.dao.nosql.job.ScheduleJobDao;
import per.rss.server.poll.model.job.ScheduleJob;

@Component
public class JobInit {
	private static final Logger logger = LoggerFactory.getLogger(JobInit.class);
	
	@Autowired
	private ScheduleJobDao scheduleJobDao;
	
	@Autowired
	private ScheduleJobBiz scheduleJobBiz;
	
	@PostConstruct
	public void init() throws Exception {
		logger.debug("Began to initialize job.");
		List<ScheduleJob> jobList=scheduleJobDao.findByJobInit();
		
		ScheduleJob scheduleJob1=new ScheduleJob();
		scheduleJob1.setJobName("MyJobName1");
		scheduleJob1.setJobGroup("MyJobGroup1");
		scheduleJob1.setCronExpression("*/5 * * * * ?");
		scheduleJob1.setIsSync(true);
		
		ScheduleJob scheduleJob2=new ScheduleJob();
		scheduleJob2.setJobName("MyJobName2");
		scheduleJob2.setJobGroup("MyJobGroup2");
		scheduleJob2.setCronExpression("*/6 * * * * ?");
		scheduleJob2.setIsSync(true);
		
		boolean r1=scheduleJobBiz.initScheduleJob(scheduleJob1);
		boolean r2=scheduleJobBiz.initScheduleJob(scheduleJob2);
		
		logger.debug("r1="+r1+",r2="+r2);
		
		logger.debug("Job initialization is complete.");
	}

	@PreDestroy
	public void destory() {
		logger.debug("Systeam user information destory is complete.");
	}
}
