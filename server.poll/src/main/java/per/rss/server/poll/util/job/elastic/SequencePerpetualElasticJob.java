package per.rss.server.poll.util.job.elastic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.AbstractSequencePerpetualElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionSingleShardingContext;

public class SequencePerpetualElasticJob extends AbstractSequencePerpetualElasticJob<Object> {
	protected static transient Logger log = LoggerFactory.getLogger(SequencePerpetualElasticJob.class);

	@Override
	protected List<Object> fetchData(JobExecutionSingleShardingContext context) {
		List<Object> result = new ArrayList<Object>();
		for (int i = 0; i < 1000; i++) {
			result.add(new Object());
		}
		return result;
	}

	@Override
	protected boolean processData(JobExecutionSingleShardingContext context, Object data) {
		log.debug("context："+context.getJobName()+"params:"+context.getJobParameter()+",data:"+data.hashCode());
		return true;
	}
}
