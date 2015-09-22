package per.rss.server.poll.util.job.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.server.poll.util.job.quartz.impl.JobAsyncFactory;
import per.rss.server.poll.util.job.quartz.impl.JobSyncFactory;

public abstract class JobFactory implements Job {
	private static final Logger logger = LoggerFactory.getLogger(JobFactory.class);
	// 类加载时就初始化
	private static final Class<? extends Job> asyncClass = JobAsyncFactory.class;
	private static final Class<? extends Job> syncClass = JobSyncFactory.class;

	protected JobFactory() {
		
	}

	public final static Class<? extends Job> getInstance(boolean isSync) {
		if (isSync) {
			return syncClass;
		} else {
			return asyncClass;
		}
	}

	public final void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug("JobFactory is start.");
		doExecute(context);
		logger.debug("JobFactory is end.");
	}

	protected abstract void doExecute(JobExecutionContext context) throws JobExecutionException;
}
