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
 * 同步的任务工厂类
 *
 * 一些注解的含义：http://www.cnblogs.com/daxin/archive/2013/05/27/3101972.html
 * 
 * 设定的时间间隔为3秒,但job执行时间是5秒,设置@DisallowConcurrentExecution以后程序会等任务执行完毕以后再去执行,
 * 否则会在3秒时再启用新的线程执行
 *
 */
@DisallowConcurrentExecution
public class JobSyncFactory extends JobFactory {
	private static final Logger logger = LoggerFactory.getLogger(JobSyncFactory.class);

	protected void doExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("JobSyncFactory is start.");
		JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
		ScheduleJob scheduleJob = (ScheduleJob) mergedJobDataMap.get(ScheduleJobBo.JOB_PARAM_KEY);
		logger.info("jobName:" + scheduleJob.getJobName() + ",jobGroup:" + scheduleJob.getJobGroup() + ",scheduleJob:"
				+ scheduleJob.toString());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			logger.error("doExecute is error.", e);
		}
		logger.info("JobSyncFactory is end.");
	}
}
