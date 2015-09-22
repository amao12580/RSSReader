package per.rss.server.poll.bo.feed;

import java.util.Date;
import java.util.Set;

import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.model.feed.Article;
import per.rss.server.poll.model.feed.Image;

public class FeedSyncBo {
	private String id = null;

	// 固有属性 开始
	private String title = null;
	private Image image = null;
	private String link = null;
	private String description = null;
	private String language = null;
	private String generator = null;
	private String lastBuildDate = null;
	private Long ttl = null;
	private String copyright = null;
	private Date pubDate = null;
	private String category = null;
	private Set<Article> item = null;

	// 固有属性 结束
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}


	public Long getTtl() {
		return ttl;
	}

	public void setTtl(Long ttl) {
		this.ttl = ttl;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Article> getItem() {
		return item;
	}

	public void setItem(Set<Article> item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
