package per.rss.core.job.simple.quartz.impl;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.job.simple.bo.job.ScheduleJobBo;
import per.rss.core.job.simple.quartz.JobFactory;

/**
 * 任务工厂类,非同步
 *
 */
public class JobAsyncFactory extends JobFactory {
	private static final Logger logger = LoggerFactory.getLogger(JobAsyncFactory.class);

	protected void doExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("JobAsyncFactory is start.");
		JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
		ScheduleJobBo scheduleJobBo = (ScheduleJobBo) mergedJobDataMap.get(ScheduleJobBo.JOB_PARAM_KEY);
		logger.info("jobName:" + scheduleJobBo.getJobName() + ",jobGroup:" + scheduleJobBo.getJobGroup() + ",scheduleJob:"
				+ scheduleJobBo.toString());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.error("doExecute is error.", e);
		}
		logger.info("JobAsyncFactory is end.");
	}
}
