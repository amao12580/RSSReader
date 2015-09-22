package per.rss.server.poll.util.job.quartz.impl;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.server.poll.bo.job.ScheduleJobBo;
import per.rss.server.poll.model.job.ScheduleJob;
import per.rss.server.poll.util.job.quartz.JobFactory;

/**
 * 同步的任务工厂类
 *
 * Created by liyd on 12/19/14.
 */
public class JobSyncFactory extends JobFactory {
	private static final Logger logger = LoggerFactory.getLogger(JobSyncFactory.class);
	
	private JobSyncFactory() {

	}
	protected void doExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.debug("JobSyncFactory is start.");
		JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
		ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get(ScheduleJobBo.JOB_PARAM_KEY);
		logger.debug("jobName:" + scheduleJob.getJobName() + "  " + scheduleJob);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.debug("doExecute is error.",e);
		}
		logger.debug("JobSyncFactory is end.");
	}
}
