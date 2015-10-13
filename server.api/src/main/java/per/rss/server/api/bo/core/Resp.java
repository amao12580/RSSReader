package per.rss.server.api.bo.core;

import java.util.List;

import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.StringUtils;

/**
 * 结果返回
 */
public class Resp {
	/**
	 * 返回码
	 */
	int code;

	/**
	 * 返回信息
	 */
	String desc = "";

	/**
	 * 返回数据
	 */
	Object result = "";

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

	public Resp(int code, String desc, Object result) {
		this.code = code;
		this.desc = desc;
		if (result == null) {
			this.result = "";
		} else {
			this.result = result;
		}
	}

	public Resp(int code, String desc, List<?> resuts) {
		this.code = code;
		this.desc = desc;
		if (CollectionUtils.isEmpty(resuts)) {
			Object[] s = new Object[0];
			this.result = s;
		} else {
			this.result = resuts;
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
//		return "Resp [code=" + code + ", desc=" + desc + ", result=" + StringUtils.toJSONString(result) + "]";
		return StringUtils.toJSONString(this);
	}

}
