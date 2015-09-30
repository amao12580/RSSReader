package per.rss.server.poll.bo.feed;

import java.util.Date;

public class LogFeedParserBo {
	private String id = null;
	private String content = null;//解析的内容
	private Date parseStartDate = null;// 系统开始解析的时间
	private Date parseEndDate = null;// 系统完成解析的时间
	private Long takeTime = null;// 解析花费的时间
	/**
	 * 1=成功 0=失败
	 */
	private Integer status = null;// 解析的结果,状态
	private FeedParseBo feedParseBo = null;// 解析的结果,状态

	private String errorMessage = null;// 解析出现错误，大致的问题

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public FeedParseBo getFeedParseBo() {
		return feedParseBo;
	}

	public void setFeedParseBo(FeedParseBo feedParseBo) {
		this.feedParseBo = feedParseBo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
