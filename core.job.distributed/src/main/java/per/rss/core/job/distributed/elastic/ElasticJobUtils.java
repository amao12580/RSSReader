package per.rss.core.job.distributed.elastic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.JobConfiguration;
import com.dangdang.ddframe.job.schedule.JobController;
import com.dangdang.ddframe.reg.base.CoordinatorRegistryCenter;

import per.rss.core.base.util.StringUtils;

/**
 * 分布式定时任务
 *
 */
@Component("elasticJobUtils")
public final class ElasticJobUtils {
	@Autowired
	private CoordinatorRegistryCenter RSSReaderRegCenter;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initJobOne(ElasticJobConfig jobConfig) {
		if (jobConfig == null) {
			return;
		}
		JobConfiguration jobConfiguration = new JobConfiguration(jobConfig.getName(), jobConfig.getTargetClass(), 10,
				jobConfig.getCronExpression());
		Object param = jobConfig.getParam();
		if (param != null) {
			jobConfiguration.setJobParameter(StringUtils.toJSONString(param));
		}
		new JobController(RSSReaderRegCenter, jobConfiguration).init();
	}
}