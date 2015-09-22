package per.rss.server.poll.util.job.quartz.impl;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.server.poll.bo.job.ScheduleJobBo;
import per.rss.server.poll.model.job.ScheduleJob;
import per.rss.server.poll.util.job.quartz.JobFactory;

/**
 * 任务工厂类,非同步
 *
 * User: liyd Date: 14-1-3 Time: 上午10:11
 */
@DisallowConcurrentExecution
public class JobAsyncFactory extends JobFactory {
	private static final Logger logger = LoggerFactory.getLogger(JobAsyncFactory.class);
	
	private JobAsyncFactory() {

	}
	protected void doExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.debug("JobAsyncFactory is start.");
		JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
		ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get(ScheduleJobBo.JOB_PARAM_KEY);
		logger.debug("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.debug("doExecute is error.",e);
		}
		logger.debug("JobAsyncFactory is end.");
	}
}
