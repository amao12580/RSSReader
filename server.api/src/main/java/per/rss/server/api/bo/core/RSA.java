package per.rss.server.api.bo.core;

public class RSA {
	private String module;
	private String empoent;

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
