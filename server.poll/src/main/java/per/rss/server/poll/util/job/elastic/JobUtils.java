package per.rss.server.poll.util.job.elastic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.JobConfiguration;
import com.dangdang.ddframe.job.schedule.JobController;
import com.dangdang.ddframe.reg.base.CoordinatorRegistryCenter;

import per.rss.core.base.util.StringUtils;

/**
 * 分布式定时任务
 * 
 * @author cifpay
 *
 */
@Component
public final class JobUtils {
	@Autowired
	private CoordinatorRegistryCenter RSSReaderRegCenter;

	@SuppressWarnings("rawtypes")
	public void initJobOne(JobConfig jobConfig) {
		if (jobConfig == null) {
			return;
		}
		JobConfiguration jobConfiguration = new JobConfiguration(jobConfig.getName(), MyOneOffElasticJob.class, 10,
				jobConfig.getCronExpression());
		Object param = jobConfig.getParam();
		if (param != null) {
			jobConfiguration.setJobParameter(StringUtils.toJSONString(param));
		}
		new JobController(RSSReaderRegCenter, jobConfiguration).init();
	}
}