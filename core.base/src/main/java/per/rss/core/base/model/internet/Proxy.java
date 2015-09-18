package per.rss.core.base.model.internet;

public class Proxy {
	private String proxy_ip = null;
	private Integer proxy_port = null;
	private String proxy_username = null;
	private String proxy_password = null;

	public String getProxy_ip() {
		return proxy_ip;
	}

	public void setProxy_ip(String proxy_ip) {
		this.proxy_ip = proxy_ip;
	}

	public Integer getProxy_port() {
		return proxy_port;
	}

	public void setProxy_port(Integer proxy_port) {
		this.proxy_port = proxy_port;
	}

	public String getProxy_username() {
		return proxy_username;
	}

	public void setProxy_username(String proxy_username) {
		this.proxy_username = proxy_username;
	}

	public String getProxy_password() {
		return proxy_password;
	}

	public void setProxy_password(String proxy_password) {
		this.proxy_password = proxy_password;
	}

}
