package per.rss.server.api.bo.user.login;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.server.api.bo.core.RSA;

public class ToLoginBo {
	private RSA rsa = new RSA();
	private String csrfToken = CommonConstant.stringBegining;

	public RSA getRsa() {
		return rsa;
	}

	public void setRsa(RSA rsa) {
		this.rsa = rsa;
	}

	public String getCsrfToken() {
		return csrfToken;
	}

	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
