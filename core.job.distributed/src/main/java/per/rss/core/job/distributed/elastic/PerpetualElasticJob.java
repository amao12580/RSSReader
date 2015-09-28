package per.rss.core.job.distributed.elastic;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.AbstractPerpetualElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;

public class PerpetualElasticJob extends AbstractPerpetualElasticJob<Object> {
	protected static transient Logger log = LoggerFactory.getLogger(PerpetualElasticJob.class);

	@Override
    protected List<Object> fetchData(JobExecutionMultipleShardingContext context) {
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < 1000; i++) {
			result.add(new Object());
		}
        return result;
    }

	@Override
	protected boolean processData(JobExecutionMultipleShardingContext context, Object data) {
		log.debug("context："+context.getJobName()+"params:"+context.getJobParameter()+",data:"+data.hashCode());
		return true;
	}
}