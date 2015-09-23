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
		ScheduleJob scheduleJob=new ScheduleJob();
		scheduleJob.setJobName("MyJobName");
		scheduleJob.setJobGroup("MyJobGroup");
		scheduleJob.setCronExpression("*/5 * * * * ?");
		scheduleJob.setIsSync(true);
		jobList.add(scheduleJob);
		scheduleJobBiz.initScheduleJob(jobList);
		logger.debug("Job initialization is complete.");
	}

	@PreDestroy
	public void destory() {
		logger.debug("Systeam user information destory is complete.");
	}
}
