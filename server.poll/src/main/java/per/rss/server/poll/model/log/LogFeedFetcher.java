package per.rss.server.poll.model.log;

import java.util.Date;

import per.rss.core.base.model.log.LogFeedRequest;

/**
 * @author cifpay
 * 
 *         抓取是指：获取网络rss信息+解析xml
 *
 */
public class LogFeedFetcher {
	private String id = null;
	private Date fetchStartDate = null;// 系统开始抓取的时间
	private Date fetchEndDate = null;// 系统完成抓取的时间
	private LogFeedRequest logFeedRequest;// 网络请求的详细信息
	private Long takeTime = null;// 尝试解析花费总的时间
	private LogFeedParser logFeedParser = null;// 解析详情
	private String charsets = null;// 字符集

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFetchStartDate() {
		return fetchStartDate;
	}

	public void setFetchStartDate(Date fetchStartDate) {
		this.fetchStartDate = fetchStartDate;
	}

	public Date getFetchEndDate() {
		return fetchEndDate;
	}

	public void setFetchEndDate(Date fetchEndDate) {
		this.fetchEndDate = fetchEndDate;
	}

	public LogFeedRequest getLogFeedRequest() {
		return logFeedRequest;
	}

	public void setLogFeedRequest(LogFeedRequest logFeedRequest) {
		this.logFeedRequest = logFeedRequest;
	}

	public Long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Long takeTime) {
		this.takeTime = takeTime;
	}

	public LogFeedParser getLogFeedParser() {
		return logFeedParser;
	}

	public void setLogFeedParser(LogFeedParser logFeedParser) {
		this.logFeedParser = logFeedParser;
	}

	public String getCharsets() {
		return charsets;
	}

	public void setCharsets(String charsets) {
		this.charsets = charsets;
	}

}
