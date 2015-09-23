package per.rss.server.poll.util.job;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.server.poll.bo.job.ScheduleJobBo;
import per.rss.server.poll.model.job.ScheduleJob;
import per.rss.server.poll.util.job.quartz.JobFactory;

/**
 * 定时任务辅助类
 * 
 */
public class ScheduleJobUtils {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobUtils.class);

	/**
	 * 获取触发器key
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	public static TriggerKey getTriggerKey(String jobName, String jobGroup) {
		return TriggerKey.triggerKey(jobName, jobGroup);
	}

	/**
	 * 获取表达式触发器
	 *
	 * @param scheduler
	 *            the scheduler
	 * @param jobName
	 *            the job name
	 * @param jobGroup
	 *            the job group
	 * @return cron trigger
	 */
	public static CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			return (CronTrigger) scheduler.getTrigger(triggerKey);
		} catch (SchedulerException e) {
			LOG.error("获取定时任务CronTrigger出现异常", e);
			return null;
		}
	}

	/**
	 * 创建任务
	 *
	 * @param scheduler
	 *            the scheduler
	 * @param scheduleJob
	 *            the schedule job
	 */
	public static boolean createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
		return createScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
				scheduleJob.getCronExpression(), scheduleJob.getIsSync(), scheduleJob);
	}

	/**
	 * 创建定时任务
	 *
	 * @param scheduler
	 *            the scheduler
	 * @param jobName
	 *            the job name
	 * @param jobGroup
	 *            the job group
	 * @param cronExpression
	 *            the cron expression
	 * @param isSync
	 *            the is sync
	 * @param param
	 *            the param
	 */
	public static boolean createScheduleJob(Scheduler scheduler, String jobName, String jobGroup, String cronExpression,
			boolean isSync, Object param) {
		boolean result = false;
		// 同步或异步
		Class<? extends Job> jobClass = JobFactory.getInstance(isSync);
		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
		// 放入参数，运行时的方法可以获取，资料：http://www.cnblogs.com/wyqtomorrow/archive/2007/04/28/730963.html
		jobDetail.getJobDataMap().put(ScheduleJobBo.JOB_PARAM_KEY, param);
		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder)
				.build();
		try {
			Date nextExexuteDate = scheduler.scheduleJob(jobDetail, trigger);// 任务创建成功，会返回下一次执行的时间
			if (nextExexuteDate != null) {
				result = true;
			}
		} catch (SchedulerException e) {
			LOG.error("创建定时任务失败", e);
			return false;
		}
		return result;
	}

	/**
	 * 运行一次任务
	 * 
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void runOnce(Scheduler scheduler, String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("运行一次定时任务失败", e);
		}
	}

	/**
	 * 暂停任务
	 * 
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("暂停定时任务失败", e);
		}
	}

	/**
	 * 恢复任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			LOG.error("暂停定时任务失败", e);
		}
	}

	/**
	 * 获取jobKey
	 *
	 * @param jobName
	 *            the job name
	 * @param jobGroup
	 *            the job group
	 * @return the job key
	 */
	public static JobKey getJobKey(String jobName, String jobGroup) {
		return JobKey.jobKey(jobName, jobGroup);
	}

	/**
	 * 更新定时任务
	 *
	 * @param scheduler
	 *            the scheduler
	 * @param scheduleJob
	 *            the schedule job
	 */
	public static boolean updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) {
		return updateScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
				scheduleJob.getCronExpression(), scheduleJob.getIsSync(), scheduleJob);
	}

	/**
	 * 更新定时任务
	 *
	 * @param scheduler
	 *            the scheduler
	 * @param jobName
	 *            the job name
	 * @param jobGroup
	 *            the job group
	 * @param cronExpression
	 *            the cron expression
	 * @param isSync
	 *            the is sync
	 * @param param
	 *            the param
	 */
	public static boolean updateScheduleJob(Scheduler scheduler, String jobName, String jobGroup, String cronExpression,
			boolean isSync, Object param) {
		boolean result = false;
		try {
			TriggerKey triggerKey = ScheduleJobUtils.getTriggerKey(jobName, jobGroup);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			Date nextExcuteDate = scheduler.rescheduleJob(triggerKey, trigger);// 任务的下一次执行时间，更新成功会返回
			if (nextExcuteDate != null) {
				result = true;
			}
		} catch (SchedulerException e) {
			LOG.error("更新定时任务失败", e);
			return false;
		}
		return result;
	}

	/**
	 * 删除定时任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static boolean deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) {
		try {
			return scheduler.deleteJob(getJobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			LOG.error("删除定时任务失败", e);
			return false;
		}
	}
}
