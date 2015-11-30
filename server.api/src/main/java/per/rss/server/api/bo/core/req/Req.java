package per.rss.server.api.bo.core.req;

import per.rss.core.base.constant.CommonConstant;

public class Req {
	private Head head = null;
	private String dataMode = CommonConstant.stringBegining;
	private String body = CommonConstant.stringBegining;
	private String digest = CommonConstant.stringBegining;
	private Long time = CommonConstant.longBegining;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public String getDataMode() {
		return dataMode;
	}

	public void setDataMode(String dataMode) {
		this.dataMode = dataMode;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Integer getCommand(Req req) {
		if (req == null) {
			return null;
		}
		Head h = req.getHead();
		if (h == null) {
			return null;
		}
		return h.getCommand();
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

}
