package per.rss.core.job.simple.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import per.rss.core.job.simple.quartz.impl.JobAsyncFactory;
import per.rss.core.job.simple.quartz.impl.JobSyncFactory;

public abstract class JobFactory implements Job {
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
		// logger.info("JobFactory is start.");
		doExecute(context);
		// logger.info("JobFactory is end.");
	}

	protected abstract void doExecute(JobExecutionContext context) throws JobExecutionException;
}
