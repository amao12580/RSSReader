package per.rss.core.job.distributed.elastic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.AbstractOneOffElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;

import per.rss.core.base.util.StringUtils;

public abstract class BaseOneOffElasticJob<T> extends AbstractOneOffElasticJob {
	protected static transient Logger log = LoggerFactory.getLogger(BaseOneOffElasticJob.class);

	/**
	 * 执行任务
	 * 
	 * @param jobParam
	 * @return
	 */
	abstract public T excuteingJob(String jobParam);

	/**
	 * 处理任务执行的结果
	 * 
	 * @param result
	 * @return
	 */
	abstract public boolean excuteingResult(T result);

	/*
	 * 任务处理的入口 (non-Javadoc)
	 * 
	 * @see com.dangdang.ddframe.job.api.AbstractOneOffElasticJob#process(com.
	 * dangdang.ddframe.job.api.JobExecutionMultipleShardingContext)
	 */
	@Override
	protected final void process(JobExecutionMultipleShardingContext context) {
		if (context == null) {
			return;
		}
		String paramStr = context.getJobParameter();
		if (StringUtils.isEmpty(paramStr)) {
			return;
		}
		T obj = excuteingJob(paramStr);
		boolean result = excuteingResult(obj);
		if (result) {
			log.debug("任务执行成功：" + context.getJobName());
		} else {
			log.error("任务执行失败：" + context.getJobName() + ",jobParam:" + context.getJobParameter());
		}
	}
}
