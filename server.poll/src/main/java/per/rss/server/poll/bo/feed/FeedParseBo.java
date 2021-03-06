package per.rss.server.poll.bo.feed;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.model.feed.piece.Article;
import per.rss.server.poll.model.feed.piece.Image;

/**
 * 仅作为解析使用
 * 
 */
public class FeedParseBo {
	private String id = CommonConstant.stringBegining;

	// 固有属性 开始
	private String title = CommonConstant.stringBegining;
	private Image image = new Image();
	private String link = CommonConstant.stringBegining;
	private String description = CommonConstant.stringBegining;
	private String language = CommonConstant.stringBegining;
	private String generator = CommonConstant.stringBegining;
	private String lastBuildDate = CommonConstant.stringBegining;
	private Long ttl = CommonConstant.longBegining;
	private String copyright = CommonConstant.stringBegining;
	private Date pubDate = CommonConstant.dateBegining;
	private String category = CommonConstant.stringBegining;
	private Set<Article> item = new HashSet<Article>(0);

	// 固有属性 结束

	private Date lastedSyncDate = CommonConstant.dateBegining;// 最后一次同步日期

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

	public Date getLastedSyncDate() {
		return lastedSyncDate;
	}

	public void setLastedSyncDate(Date lastedSyncDate) {
		this.lastedSyncDate = lastedSyncDate;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
