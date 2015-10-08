package per.rss.server.poll.bo.feed;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;

public class FeedSyncBo {
	private String id = CommonConstant.stringBegining;// feed的编号
	private String url = CommonConstant.stringBegining;// feed的rss xml文件链接地址

	private LogFeedSyncBo logFeedSync = null;// 抓取的结果

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
