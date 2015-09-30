package per.rss.server.poll.model.log;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import per.rss.core.base.constant.CommonConstant;

@Document(collection = "logFeedFetcher")
public class LogFeedFetcher implements Serializable {
	private static final long serialVersionUID = -7996518274509708501L;
	@Id
	private String id = "";
	private String feedId = "";
	private Date requestStartDate = CommonConstant.dateBegining;// 开始获取网络rss信息的时间
	private Date requestEndDate = CommonConstant.dateBegining;// 完成获取网络rss信息的时间
	private Long takeTime = -1l;// 网络请求花费的时间
	/**
	 * 1=成功 0=失败
	 */
	private Integer requestStatus = -1;// 网络请求发起的结果
	private String requestErrorMessage = "";// 网络请求发起出现错误，记录简要信息
	/**
	 * 1=成功 0=失败
	 */
	private Integer responseStatus = -1;// 网络响应的结果
	private Integer responseCode = -1;// 网络响应的结果 状态码：200、500、304、404等等

	private String responseHtml = "";// 网络响应的结果，html
	private Integer htmlLength = -1;// 网络响应的结果，数据长度

	public LogFeedFetcher doException(LogFeedFetcher logFeedRequest, Exception e) {
		logFeedRequest.setRequestStatus(CommonConstant.status.failed.getCode());
		logFeedRequest.setRequestErrorMessage(e.getMessage());
		return logFeedRequest;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public Date getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public Long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Long takeTime) {
		this.takeTime = takeTime;
	}

	public Integer getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(Integer requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestErrorMessage() {
		return requestErrorMessage;
	}

	public void setRequestErrorMessage(String requestErrorMessage) {
		this.requestErrorMessage = requestErrorMessage;
	}

	public Integer getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(Integer responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseHtml() {
		return responseHtml;
	}

	public void setResponseHtml(String responseHtml) {
		this.responseHtml = responseHtml;
	}

	public Integer getHtmlLength() {
		return htmlLength;
	}

	public void setHtmlLength(Integer htmlLength) {
		this.htmlLength = htmlLength;
	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

}
