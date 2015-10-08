package per.rss.server.poll.model.feed.piece;

import java.util.Date;

import per.rss.core.base.constant.CommonConstant;

/**
 * 收录时的信息
 * 
 * @author cifpay
 *
 */
public class Create {
	private String userId = CommonConstant.stringBegining;// 系统收录时申请者账户id
	private String ip = CommonConstant.stringBegining;// 系统收录时申请者ip
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
