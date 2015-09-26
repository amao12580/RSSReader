package per.rss.server.poll.bo.feed;

import java.util.Date;

import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.model.log.LogFeedSync;

public class FeedSyncBo {
	private String id = null;
	private String link = null;
	private Date lastedSyncDate = null;// 最后一次同步日期

	private LogFeedSync logFeedSync = null;// 抓取的结果

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getLastedSyncDate() {
		return lastedSyncDate;
	}

	public void setLastedSyncDate(Date lastedSyncDate) {
		this.lastedSyncDate = lastedSyncDate;
	}

	public LogFeedSync getLogFeedSync() {
		return logFeedSync;
	}

	public void setLogFeedSync(LogFeedSync logFeedSync) {
		this.logFeedSync = logFeedSync;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
