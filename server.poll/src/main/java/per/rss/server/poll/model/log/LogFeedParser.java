package per.rss.server.poll.model.log;

import java.util.Date;

import per.rss.server.poll.model.feed.Feed;

public class LogFeedParser {
	private Date parseStartDate = null;// 系统开始解析的时间
	private Date parseEndDate = null;// 系统完成解析的时间
	private Long takeTime = null;// 解析花费的时间
	/**
	 * 1=成功 0=失败
	 */
	private Integer status = null;// 解析的结果,状态
	private Feed feed = null;// 解析的结果,具体的数据
	private String errorMessage = null;// 解析出现错误，大致的问题

	public Date getParseStartDate() {
		return parseStartDate;
	}

	public void setParseStartDate(Date parseStartDate) {
		this.parseStartDate = parseStartDate;
	}

	public Date getParseEndDate() {
		return parseEndDate;
	}

	public void setParseEndDate(Date parseEndDate) {
		this.parseEndDate = parseEndDate;
	}

	public Long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Long takeTime) {
		this.takeTime = takeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
