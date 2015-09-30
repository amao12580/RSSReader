package per.rss.server.poll.bo.feed;

import per.rss.core.base.util.StringUtils;

public class FeedSyncBo {
	private String id = null;// feed的编号
	private String link = null;// feed的链接
	private Long lastedSyncDateTime = null;// 最后一次同步日期//必须用long

	private LogFeedSyncBo logFeedSync = null;// 抓取的结果

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

	public Long getLastedSyncDateTime() {
		return lastedSyncDateTime;
	}

	public void setLastedSyncDateTime(Long lastedSyncDateTime) {
		this.lastedSyncDateTime = lastedSyncDateTime;
	}

	public LogFeedSyncBo getLogFeedSync() {
		return logFeedSync;
	}

	public void setLogFeedSync(LogFeedSyncBo logFeedSync) {
		this.logFeedSync = logFeedSync;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
