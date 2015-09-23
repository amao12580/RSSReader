package per.rss.server.poll.biz.job.impl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.ObjectUtil;
import per.rss.server.poll.biz.job.ScheduleJobBiz;
import per.rss.server.poll.bo.job.ScheduleJobBo;
import per.rss.server.poll.model.job.ScheduleJob;
import per.rss.server.poll.util.job.ScheduleJobUtils;

/**
 * 定时任务
 *
 * Created by liyd on 12/19/14.
 */
@Service("scheduleJobBiz")
public class ScheduleJobBizImpl implements ScheduleJobBiz {

	/** 调度工厂Bean */
	@Autowired
	private Scheduler scheduler;

	/** 通用dao */
	// @Autowired
	// private JdbcDao jdbcDao;

	public void initScheduleJob(List<ScheduleJob> scheduleJobList) {
		if (CollectionUtils.isEmpty(scheduleJobList)) {
			return;
		}
		for (ScheduleJob scheduleJob : scheduleJobList) {

			CronTrigger cronTrigger = ScheduleJobUtils.getCronTrigger(scheduler, scheduleJob.getJobName(),
					scheduleJob.getJobGroup());
			// 不存在，创建一个
			if (cronTrigger == null) {
				ScheduleJobUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				// 已存在，那么更新相应的定时设置
				ScheduleJobUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	public Long insert(ScheduleJobBo scheduleJobBo) {
		ScheduleJob scheduleJob = ObjectUtil.copyProperties(scheduleJobBo, ScheduleJob.class);
		ScheduleJobUtils.createScheduleJob(scheduler, scheduleJob);
		// return jdbcDao.insert(scheduleJob);
		return 0l;
	}

	public void update(ScheduleJobBo scheduleJobBo) {
		ScheduleJob scheduleJob = ObjectUtil.copyProperties(scheduleJobBo, ScheduleJob.class);
		ScheduleJobUtils.updateScheduleJob(scheduler, scheduleJob);
		// jdbcDao.update(scheduleJob);
	}

	public void delUpdate(ScheduleJobBo scheduleJobBo) {
		ScheduleJob scheduleJob = ObjectUtil.copyProperties(scheduleJobBo, ScheduleJob.class);
		// 先删除
		ScheduleJobUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
		// 再创建
		ScheduleJobUtils.createScheduleJob(scheduler, scheduleJob);
		// 数据库直接更新即可
		// jdbcDao.update(scheduleJob);
	}

	public void delete(Long scheduleJobId) {
		// ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class,
		// scheduleJobId);
		// 删除运行的任务
		// ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(),
		// scheduleJob.getJobGroup());
		// 删除数据
		// jdbcDao.delete(ScheduleJob.class, scheduleJobId);
	}

	public void runOnce(Long scheduleJobId) {

		// ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class,
		// scheduleJobId);
		// ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(),
		// scheduleJob.getJobGroup());
	}

	public void pauseJob(Long scheduleJobId) {

		// ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class,
		// scheduleJobId);
		// ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(),
		// scheduleJob.getJobGroup());

		// 演示数据库就不更新了
	}

	public void resumeJob(Long scheduleJobId) {
		// ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class,
		// scheduleJobId);
		// ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(),
		// scheduleJob.getJobGroup());

		// 演示数据库就不更新了
	}

	public ScheduleJobBo get(Long scheduleJobId) {
		// ScheduleJob scheduleJob = jdbcDao.get(ScheduleJob.class,
		// scheduleJobId);
		// return scheduleJob.getTargetObject(scheduleJobBo.class);
		return null;
	}

	public List<ScheduleJobBo> queryList(ScheduleJobBo scheduleJobBo) {

		// List<ScheduleJob> scheduleJobs = jdbcDao.queryList(scheduleJobBo
		// .getTargetObject(ScheduleJob.class));
		// List<ScheduleJob> scheduleJobs = new ArrayList<ScheduleJob>();

		// List<scheduleJobBo> scheduleJobBoList =
		// BeanConverter.convert(scheduleJobBo.class,scheduleJobs);
		List<ScheduleJobBo> scheduleJobBoList = new ArrayList<ScheduleJobBo>();
		try {
			for (ScheduleJobBo vo : scheduleJobBoList) {

				JobKey jobKey = ScheduleJobUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				if (CollectionUtils.isEmpty(triggers)) {
					continue;
				}

				// 这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
				Trigger trigger = triggers.iterator().next();
				scheduleJobBo.setJobTrigger(trigger.getKey().getName());

				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				vo.setStatus(triggerState.name());

				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					vo.setCronExpression(cronExpression);
				}
			}
		} catch (SchedulerException e) {
			// 演示用，就不处理了
		}
		return scheduleJobBoList;
	}

	public List<ScheduleJobBo> queryExecutingJobList() {
		try {
			List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			List<ScheduleJobBo> jobList = new ArrayList<ScheduleJobBo>(executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				ScheduleJobBo job = new ScheduleJobBo();
				JobDetail jobDetail = executingJob.getJobDetail();
				JobKey jobKey = jobDetail.getKey();
				Trigger trigger = executingJob.getTrigger();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setJobTrigger(trigger.getKey().getName());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
			return jobList;
		} catch (SchedulerException e) {
			// 演示用，就不处理了
			return null;
		}

	}
}
