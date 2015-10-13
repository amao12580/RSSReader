package per.rss.server.api.bo.core;

import per.rss.core.base.constant.CommonConstant;

public class RSA {
	private String module = CommonConstant.stringBegining;
	private String empoent = CommonConstant.stringBegining;

	public RSA() {
	}

	public RSA(String module, String empoent) {
		this.module = module;
		this.empoent = empoent;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getEmpoent() {
		return empoent;
	}

	public void setEmpoent(String empoent) {
		this.empoent = empoent;
	}

}
