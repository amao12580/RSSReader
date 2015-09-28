package per.rss.server.poll.dao.job;

import java.util.List;

import per.rss.server.poll.model.job.ScheduleJob;

/**
 * @author cifpay
 */
public interface ScheduleJobDao {
	/**
	 * 生成ID
	 * @return
	 */
	int generateUserID();
	
	List<ScheduleJob> findByJobInit();
}
