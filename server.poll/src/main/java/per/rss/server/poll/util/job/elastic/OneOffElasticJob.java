package per.rss.server.poll.util.job.elastic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.AbstractOneOffElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;

public class OneOffElasticJob extends AbstractOneOffElasticJob {
	protected static transient Logger log = LoggerFactory.getLogger(OneOffElasticJob.class);

	@Override
	protected void process(JobExecutionMultipleShardingContext context) {
		String paramStr = context.getJobParameter();
		log.debug("end context：" + context.getJobName() + ",params:" + paramStr);
	}
}