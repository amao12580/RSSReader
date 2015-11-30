package per.rss.server.api.bo.core.resp;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.UUIDUtils;

public class Head {
	private String messageId = CommonConstant.stringBegining;
	private Integer code = CommonConstant.intBegining;
	private String desc = CommonConstant.stringBegining;

	public Head(Integer code, String desc) {
		this.messageId = UUIDUtils.randomUUID();
		this.code = code;
		this.desc = desc;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
