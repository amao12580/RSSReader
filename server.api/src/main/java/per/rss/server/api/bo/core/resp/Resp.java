package per.rss.server.api.bo.core.resp;

import java.util.List;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.StringUtils;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.constant.InterfaceConstant;
import per.rss.server.api.util.SHA256Utils;
import per.rss.server.api.util.code.impl.Base64;

/**
 * 结果返回
 */
public class Resp {
	private Head head = null;
	private String dataMode = CommonConstant.stringBegining;
	private String body = CommonConstant.stringBegining;
	private String digest = CommonConstant.stringBegining;
	private Long time = CommonConstant.longBegining;

	public Resp() {
		this(Error.message.common_success);
	}

	public Resp(Error.message message) {
		this(message.getCode(), message.getDesc());
	}

	public Resp(Object result) {
		this(Error.message.common_success.getCode(), Error.message.common_success.getDesc(), result);
	}

	public Resp(List<?> resuts) {
		this(Error.message.common_success.getCode(), Error.message.common_success.getDesc(), resuts);
	}

	public Resp(int code, String desc) {
		this(code, desc, "");
	}
	
	public Resp(int code, String desc, List<?> resuts) {
		Object myResult = null;
		if (CollectionUtils.isEmpty(resuts)) {
			Object[] s = new Object[0];
			myResult = s;
		} else {
			myResult = resuts;
		}
		new Resp(code, desc, myResult);
	}

	public Resp(int code, String desc, Object result) {
		if (result == null) {
			result = "";
		}
		this.head = new Head(code, desc);
		this.dataMode = this.choseDataMode();
		String resultStr = StringUtils.toJSONString(result);
		this.body = this.encryptData(resultStr);
		this.digest = this.dataDigest(resultStr);
		this.time = System.currentTimeMillis();
	}

	private String dataDigest(String result) {
		return SHA256Utils.encrypt(result);
	}

	private String encryptData(String result) {
		String dataMode = this.dataMode;
		if (dataMode.equals(InterfaceConstant.dataMode.level_one.getModel())) {
			return Base64.encrypt(result);
		}
		return null;
	}

	private String choseDataMode() {
		return InterfaceConstant.dataMode.level_one.getModel();
	}

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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}

	public int code() {
		Head head1 = this.head;
		if (head1 == null) {
			return Error.message.common_failed.getCode();
		}
		return head1.getCode();
	}
}
