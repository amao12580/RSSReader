package per.rss.server.api.bo.core.req;

import per.rss.core.base.constant.CommonConstant;

public class Head {
	private Integer command = CommonConstant.intBegining;
	private String messageId = CommonConstant.stringBegining;
	private String activeId = CommonConstant.stringBegining;
	private String sessionId = CommonConstant.stringBegining;
	private String sessionSign = CommonConstant.stringBegining;

	public Integer getCommand() {
		return command;
	}

	public void setCommand(Integer command) {
		this.command = command;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionSign() {
		return sessionSign;
	}

	public void setSessionSign(String sessionSign) {
		this.sessionSign = sessionSign;
	}

}
