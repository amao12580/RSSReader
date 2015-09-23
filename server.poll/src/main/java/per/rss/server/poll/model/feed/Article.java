package per.rss.server.poll.model.feed;

import java.util.Date;

import per.rss.core.base.util.StringUtils;

/**
 * 文章
 * 
 * @author cifpay
 *
 */
public class Article {
	private String id = null;
	private String feedId = null;

	// 固有属性 开始
	private String title = null;
	private String link = null;
	private Date pubDate = null;
	private String source = null;
	private String author = null;
	private String description = null;
	private String category = null;
	private String comments = null;
	
	
	// 固有属性 结束

	private Date fetchDate = null;// 接受日期:文章被系统获取到的时间

	
	
	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFetchDate() {
		return fetchDate;
	}

	public void setFetchDate(Date fetchDate) {
		this.fetchDate = fetchDate;
	}
	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}