package per.rss.server.poll.model.feed;

import java.util.Date;

import per.rss.core.base.constant.CommonConstant;

public class FeedCreate {
	private String userId = "";// 系统收录时申请者id
	private String ip = "";// 系统收录时申请者id
	private String url = "";// 系统收录时使用的URL
	private Date date = CommonConstant.dateBegining;// 系统收录的日期

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
