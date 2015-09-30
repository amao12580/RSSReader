package per.rss.server.poll.model.feed;

import per.rss.core.base.util.StringUtils;

public class Image {
	// 固有属性 开始
	private String title = "";
	private String link = "";
	private String url = "";

	// 固有属性 结束
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
