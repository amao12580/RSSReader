package per.rss.server.api.bo.core;

import java.util.Map;

import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.StringUtils;

public class Browser {
	/**
	 * 浏览器类型
	 */
	private String type;
	/**
	 * 浏览器版本
	 */
	private String version;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static Browser toBrowser(Map<String, String> map) {
		if (CollectionUtils.isEmpty(map)) {
			return null;
		}
		if (map.size() != 2) {
			return null;
		}
		Browser b = new Browser();
		for (String key : map.keySet()) {
			if (!StringUtils.isEmpty(key)) {
				if ("type".equals(key)) {
					b.setType(map.get(key));
				} else if ("version".equals(key)) {
					b.setVersion(map.get(key));
				}
			}
		}
		if (StringUtils.isEmpty(b.getType())) {
			return null;
		}
		if (StringUtils.isEmpty(b.getVersion())) {
			return null;
		}
		return b;
	}
}
