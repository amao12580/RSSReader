package per.rss.server.api.bo.user.login;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;

public class LoginSuccessCookieBo {
	/**
	 * cookie值：用户uid
	 */
	private String uid = CommonConstant.stringBegining;
	/**
	 * 限定使用的ip
	 */
	private String ip = CommonConstant.stringBegining;
	/**
	 * 过期时间
	 */
	private long eTime = CommonConstant.longBegining;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long geteTime() {
		return eTime;
	}

	public void seteTime(long eTime) {
		this.eTime = eTime;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
