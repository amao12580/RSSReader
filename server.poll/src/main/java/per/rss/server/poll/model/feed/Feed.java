package per.rss.server.poll.model.feed;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;

@Document(collection = "feed")
public class Feed implements Serializable {
	private static final long serialVersionUID = -3244400551015983688L;

	@Id
	private String id = "";

	// 固有属性 开始
	private String title = "";
	private Image image = new Image();
	private String link = "";
	private String description = "";
	private String language = "";
	private String generator = "";
	private String lastBuildDate = "";
	private Long ttl = -1l;
	private String copyright = "";
	private Date pubDate = CommonConstant.dateBegining;
	private String category = "";
	// 固有属性 结束

	private Integer lastedSyncArticleSum = -1;// 最后一次同步的文章数量
	private Date lastedSyncDate = CommonConstant.dateBegining;// 最后一次同步日期
	private FeedCreate feedCreate = new FeedCreate();// 收录时的信息

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

	public Integer getLastedSyncArticleSum() {
		return lastedSyncArticleSum;
	}

	public void setLastedSyncArticleSum(Integer lastedSyncArticleSum) {
		this.lastedSyncArticleSum = lastedSyncArticleSum;
	}

	// @JsonFormat(
	// timezone=CommonConstant.DatetimeTimeZone_Default,pattern=CommonConstant.DatetimePattern_Full)
	public Date getLastedSyncDate() {
		return lastedSyncDate;
	}

	public void setLastedSyncDate(Date lastedSyncDate) {
		this.lastedSyncDate = lastedSyncDate;
	}

	public FeedCreate getFeedCreate() {
		return feedCreate;
	}

	public void setFeedCreate(FeedCreate feedCreate) {
		this.feedCreate = feedCreate;
	}

	@Override
	public String toString() {
		return StringUtils.toJSONString(this);
	}
}
