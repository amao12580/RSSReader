package per.rss.server.poll.util.job.elastic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.AbstractOneOffElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;

import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.model.feed.Feed;

public class MyOneOffElasticJob extends AbstractOneOffElasticJob {
	protected static transient Logger log = LoggerFactory.getLogger(MyOneOffElasticJob.class);

	@Override
	protected void process(JobExecutionMultipleShardingContext context) {
		String paramStr=context.getJobParameter();
		log.debug("begin context："+context.getJobName()+",params:"+paramStr);
		Feed feed=StringUtils.formatJson(paramStr,Feed.class);
//		log.debug("id："+feed.getId()+",name:"+feed.getLink()+",param:"+feed.toString());
		try {
			Thread.sleep(20*1000);
		} catch (InterruptedException e) {
			log.error("InterruptedException",e);
		}
		log.debug("end context："+context.getJobName()+",params:"+paramStr);
	}
}