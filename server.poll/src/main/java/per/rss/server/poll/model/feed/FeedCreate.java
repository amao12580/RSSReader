package per.rss.server.poll.model.feed;

import java.util.Date;

public class FeedCreate {
	private String userId = null;// 系统收录时申请者id
	private String ip = null;// 系统收录时申请者id
	private String link = null;// 系统收录时使用的URL
	private Date date = null;// 系统收录的日期

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
