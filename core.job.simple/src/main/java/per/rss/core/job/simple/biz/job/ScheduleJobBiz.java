package per.rss.core.job.simple.biz.job;

import java.util.List;

import per.rss.core.job.simple.bo.job.ScheduleJobBo;

/**
 * 定时任务service
 *
 */
public interface ScheduleJobBiz {

	/**
	 * 初始化定时任务
	 */
	public boolean initScheduleJob(ScheduleJobBo scheduleJobBo);

	/**
	 * 新增
	 * 
	 * @param scheduleJobBo
	 * @return
	 */
	public Long insert(ScheduleJobBo scheduleJobBo);

	/**
	 * 直接修改 只能修改运行的时间，参数、同异步等无法修改
	 * 
	 * @param scheduleJobBo
	 */
	public boolean update(ScheduleJobBo scheduleJobBo);

	/**
	 * 删除重新创建方式
	 * 
	 * @param scheduleJobBo
	 */
	public boolean delUpdate(ScheduleJobBo scheduleJobBo);

	/**
	 * 删除
	 * 
	 * @param scheduleJobId
	 */
	public void delete(Long scheduleJobId);

	/**
	 * 运行一次任务
	 *
	 * @param scheduleJobId
	 *            the schedule job id
	 * @return
	 */
	public void runOnce(Long scheduleJobId);

	/**
	 * 暂停任务
	 *
	 * @param scheduleJobId
	 *            the schedule job id
	 * @return
	 */
	public void pauseJob(Long scheduleJobId);

	/**
	 * 恢复任务
	 *
	 * @param scheduleJobId
	 *            the schedule job id
	 * @return
	 */
	public void resumeJob(Long scheduleJobId);

	/**
	 * 获取任务对象
	 * 
	 * @param scheduleJobId
	 * @return
	 */
	public ScheduleJobBo get(Long scheduleJobId);

	/**
	 * 查询任务列表
	 * 
	 * @param scheduleJobBo
	 * @return
	 */
	public List<ScheduleJobBo> queryList(ScheduleJobBo scheduleJobBo);

	/**
	 * 获取运行中的任务列表
	 *
	 * @return
	 */
	public List<ScheduleJobBo> queryExecutingJobList();

}
