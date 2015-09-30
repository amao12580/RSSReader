package per.rss.server.poll.model.log;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import per.rss.core.base.constant.CommonConstant;

@Document(collection = "logFeedParser")
public class LogFeedParser implements Serializable {
	private static final long serialVersionUID = -2787303135330536824L;
	@Id
	private String id = "";
	private String feedId = "";
	private String content = null;// 解析的内容
	private Date parseStartDate = CommonConstant.dateBegining;// 系统开始解析的时间
	private Date parseEndDate = CommonConstant.dateBegining;// 系统完成解析的时间
	private Long takeTime = -1l;// 解析花费的时间
	/**
	 * 1=成功 0=失败
	 */
	private Integer status = -1;// 解析的结果,状态
	private String errorMessage = "";// 解析出现错误，大致的问题

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
