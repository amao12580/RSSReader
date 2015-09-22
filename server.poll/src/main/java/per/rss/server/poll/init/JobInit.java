package per.rss.server.poll.init;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.rss.server.poll.dao.nosql.job.ScheduleJobDao;

@Service
public class JobInit {
	private static final Logger logger = LoggerFactory.getLogger(JobInit.class);
	
	@Autowired
	private ScheduleJobDao scheduleJobDao;
	
	@PostConstruct
	public void init() throws Exception {
		logger.debug("Began to initialize job.");
		scheduleJobDao.findByJobInit();
		logger.debug("Job initialization is complete.");
	}

	@PreDestroy
	public void destory() {
		logger.debug("Systeam user information destory is complete.");
	}
}
