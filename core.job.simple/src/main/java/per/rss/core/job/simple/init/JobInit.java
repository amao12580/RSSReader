package per.rss.core.job.simple.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import per.rss.core.job.simple.biz.job.ScheduleJobBiz;
import per.rss.core.job.simple.bo.job.ScheduleJobBo;

//@Component
public class JobInit {
	private static final Logger logger = LoggerFactory.getLogger(JobInit.class);

	@Autowired
	private ScheduleJobBiz scheduleJobBiz;

	// @PostConstruct
	public void init() throws Exception {
		logger.debug("Began to initialize job.");
		// List<ScheduleJob> jobList = scheduleJobDao.findByJobInit();

		ScheduleJobBo scheduleJob1 = new ScheduleJobBo();
		scheduleJob1.setJobName("MyJobName1");
		scheduleJob1.setJobGroup("MyJobGroup1");
		scheduleJob1.setCronExpression("*/5 * * * * ?");
		scheduleJob1.setIsSync(true);

		ScheduleJobBo scheduleJob2 = new ScheduleJobBo();
		scheduleJob2.setJobName("MyJobName2");
		scheduleJob2.setJobGroup("MyJobGroup2");
		scheduleJob2.setCronExpression("*/6 * * * * ?");
		scheduleJob2.setIsSync(true);

		boolean r1 = scheduleJobBiz.initScheduleJob(scheduleJob1);
		boolean r2 = scheduleJobBiz.initScheduleJob(scheduleJob2);

		logger.debug("r1=" + r1 + ",r2=" + r2);

		logger.debug("Job initialization is complete.");
	}

	// @PreDestroy
	public void destory() {
		logger.debug("Systeam job information destory is complete.");
	}
}
