package per.rss.core.base.quartz.local.job.quartz.impl;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.bo.job.ScheduleJobBo;
import per.rss.core.base.model.job.ScheduleJob;
import per.rss.core.base.quartz.local.job.quartz.JobFactory;

/**
 * 任务工厂类,非同步
 *
 */
public class JobAsyncFactory extends JobFactory {
	private static final Logger logger = LoggerFactory.getLogger(JobAsyncFactory.class);

	protected void doExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("JobAsyncFactory is start.");
		JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
		ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get(ScheduleJobBo.JOB_PARAM_KEY);
		logger.info("jobName:" + scheduleJob.getJobName() + ",jobGroup:" + scheduleJob.getJobGroup() + ",scheduleJob:"
				+ scheduleJob.toString());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.error("doExecute is error.", e);
		}
		logger.info("JobAsyncFactory is end.");
	}
}
