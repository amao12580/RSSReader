package per.rss.server.poll.util.xml.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.constant.FeedSyncConstant;
import per.rss.core.base.util.CollectionUtils;
import per.rss.core.base.util.StringUtils;
import per.rss.core.base.util.UUIDUtils;
import per.rss.server.poll.bo.feed.FeedParseBo;
import per.rss.server.poll.model.feed.Article;
import per.rss.server.poll.model.feed.Image;
import per.rss.server.poll.util.xml.XMLHandler;

/**
 * @author cifpay
 * 
 *         新方案，极强的兼容性
 * 
 *         http://whmwg.iteye.com/blog/1750509
 *
 */
public class JsoupXMLHandler extends XMLHandler {

	private static final Logger logger = LoggerFactory.getLogger(JsoupXMLHandler.class);

	public JsoupXMLHandler() {

	}

	@Override
	protected FeedParseBo doParseXML(String feedId, Date lastedSyncDate, String xml) {
		// logger.debug("LastedSyncDate：" + lastedSyncDate.getTime());
		FeedParseBo feedParseBo = null;
		Document doc = Jsoup.parse(xml, "", Parser.xmlParser());// xml方式的解析
		Elements rss = doc.select("rss");
		if (CollectionUtils.isEmpty(rss) || rss.size() != 1) {
			logger.error("rss node is empty.");
			return null;
		}
		// logger.debug("rss node size is:" + rss.size());
		Elements channel = doc.select("channel");
		if (CollectionUtils.isEmpty(rss) || rss.size() != 1) {
			logger.error("rss->channel node is empty.");
			return null;
		}
		// logger.debug("channel node size is:" + channel.size());
		feedParseBo = new FeedParseBo();
		feedParseBo.setId(feedId);
		Elements title = channel.select("title");// 非必需节点
		if (CollectionUtils.isEmpty(title)) {
			// logger.warn("rss->channel->title is empty.");
		} else {
			// logger.debug("title size is:" + title.size());
			String titleFirst = title.first().text();
			// logger.debug("title first is:" + titleFirst);
			feedParseBo.setTitle(titleFirst);
		}
		Elements image = channel.select("image");// 非必需节点
		if (CollectionUtils.isEmpty(image)) {
			// logger.warn("rss->channel->image is empty.");
		} else {
			// logger.debug("image size is:" + image.size());
			Image imageInfo = new Image();
			Elements imageTitle = image.first().select("title");// 非必需节点
			if (CollectionUtils.isEmpty(imageTitle)) {
				// logger.warn("rss->channel->image->title is empty.");
			}
			// logger.debug("imageTitle size is:" + imageTitle.size());
			String imageTitleFirst = imageTitle.first().text();
			if (StringUtils.isEmpty(imageTitleFirst)) {
				// logger.warn("image title first is empty.");
			} else {
				// logger.debug("image title first is:" + imageTitleFirst);
				imageInfo.setTitle(imageTitleFirst);
			}
			Elements imagelink = image.first().select("link");// 非必需节点
			if (CollectionUtils.isEmpty(imagelink)) {
				// logger.warn("rss->channel->image->link is empty.");
			}
			// logger.debug("imagelink size is:" + imagelink.size());
			String imagelinkFirst = imagelink.first().text();
			if (StringUtils.isEmpty(imagelinkFirst)) {
				// logger.warn("image link first is empty.");
			} else {
				// logger.debug("image link first is:" + imagelinkFirst);
				imageInfo.setLink(imagelinkFirst);
			}
			Elements imageurl = image.first().select("url");// 非必需节点
			if (CollectionUtils.isEmpty(imageurl)) {
				// logger.warn("rss->channel->image->url is empty.");
			}
			// logger.debug("imageurl size is:" + imageurl.size());
			String imageurlFirst = imageurl.first().text();
			if (StringUtils.isEmpty(imageurlFirst)) {
				// logger.warn("image url first is empty.");
			} else {
				// logger.debug("image url first is:" + imageurlFirst);
				imageInfo.setUrl(imageurlFirst);
			}
			feedParseBo.setImage(imageInfo);
		}
		Elements link = channel.select("link");
		if (CollectionUtils.isEmpty(link)) {
			logger.error("rss->channel->link is empty.");
			return null;
		}
		// logger.debug("link size is:" + link.size());
		String linkFirst = link.first().text();
		// logger.debug("link first is:" + linkFirst);
		feedParseBo.setLink(linkFirst);
		Elements description = channel.select("description");// 非必需节点
		if (CollectionUtils.isEmpty(description)) {
			// logger.warn("rss->channel->description is empty.");
		} else {
			// logger.debug("description size is:" + description.size());
			String descriptionFirst = description.first().text();
			// logger.debug("description first is:" + descriptionFirst);
			feedParseBo.setDescription(descriptionFirst);
		}
		Elements language = channel.select("language");// 非必需节点
		if (CollectionUtils.isEmpty(language)) {
			// logger.warn("rss->channel->language is empty.");
		} else {
			// logger.debug("language size is:" + language.size());
			String languageFirst = language.first().text();
			// logger.debug("language first is:" + languageFirst);
			feedParseBo.setLanguage(languageFirst);
		}
		Elements generator = channel.select("generator");// 非必需节点
		if (CollectionUtils.isEmpty(generator)) {
			// logger.warn("rss->channel->generator is empty.");
		} else {
			// logger.debug("generator size is:" + generator.size());
			String generatorFirst = generator.first().text();
			// logger.debug("generator first is:" + generatorFirst);
			feedParseBo.setGenerator(generatorFirst);
		}
		Elements lastBuildDate = channel.select("lastBuildDate");// 非必需节点
		if (CollectionUtils.isEmpty(lastBuildDate)) {
			// logger.warn("rss->channel->lastBuildDate is empty.");
		} else {
			// logger.debug("lastBuildDate size is:" +
			// lastBuildDate.size());
			String lastBuildDateFirst = lastBuildDate.first().text();
			// logger.debug("lastBuildDate first is:" + lastBuildDateFirst);
			feedParseBo.setLastBuildDate(lastBuildDateFirst);
		}
		Elements copyright = channel.select("copyright");// 非必需节点
		if (CollectionUtils.isEmpty(copyright)) {
			// logger.warn("rss->channel->copyright is empty.");
		} else {
			// logger.debug("copyright size is:" + copyright.size());
			String copyrightFirst = copyright.first().text();
			// logger.debug("copyright first is:" + copyrightFirst);
			feedParseBo.setCopyright(copyrightFirst);
		}
		Elements category = channel.select("category");// 非必需节点
		if (CollectionUtils.isEmpty(category)) {
			// logger.warn("rss->channel->category is empty.");
		} else {
			// logger.debug("category size is:" + category.size());
			String categoryFirst = category.first().text();
			// logger.debug("category first is:" + categoryFirst);
			feedParseBo.setCategory(categoryFirst);
		}
		Elements ttl = channel.select("ttl");// 非必需节点
		if (CollectionUtils.isEmpty(ttl)) {
			// logger.warn("rss->channel->ttl is empty.");
		} else {
			// logger.debug("ttl size is:" + ttl.size());
			String ttlFirst = ttl.first().text();
			// logger.debug("ttl first is:" + ttlFirst);
			feedParseBo.setTtl(Long.valueOf(ttlFirst));
		}
		Elements item = channel.select("item");
		if (CollectionUtils.isEmpty(item)) {
			logger.error("rss->channel->item is empty.");
			return null;
		}
		// logger.debug("item size is:" + item.size());
		boolean needCheckPubDate = false;
		long lastedSyncDateTime = 0l;
		Date now = new Date();
		if (lastedSyncDate != null) {// lastedSyncDate参数是有效值，并且小于当前时间。
			lastedSyncDateTime = lastedSyncDate.getTime();
			if (lastedSyncDateTime - now.getTime() < 0) {
				needCheckPubDate = true;
			}
		}
		Set<Article> articles = new HashSet<Article>(item.size());
		// for (Element ele : item) {
		for (int i = 0; i < item.size(); i++) {
			Element ele = item.get(i);
			Elements articlepubDate = ele.select("pubDate");// 非必需节点
			Article article = null;
			// 优先解析pubDate
			if (CollectionUtils.isEmpty(articlepubDate)) {
				// logger.warn("rss->channel->item->pubDate is empty.");
				continue;
			} else {
				// logger.debug("articlepubDate size is:" +
				// articlepubDate.size());
				String articlepubDateFirstString = articlepubDate.first().text();
				// logger.debug("articlepubDate first is:" +
				// articlepubDateFirst);
				Date pubDate = super.parsePubDateString(articlepubDateFirstString);
				if (pubDate == null) {
					continue;
				}
				if (needCheckPubDate) {
					if ((lastedSyncDateTime - pubDate.getTime()) > 0) {
						// 如果有文章节点没有更新的内容，则以下的节点都不用解析了
						break;
					}
				}
				article = new Article();
				article.setPubDate(pubDate);
			}
			article.setId(UUIDUtils.randomUUID());
			article.setFeedId(feedId);
			article.setFetchDate(now);
			Elements articletitle = ele.select("title");// 非必需节点
			if (CollectionUtils.isEmpty(articletitle)) {
				// logger.warn("rss->channel->item->title is empty.");
			} else {
				// logger.debug("articletitle size is:" +
				// articletitle.size());
				String articletitleFirst = articletitle.first().text();
				// logger.debug("articletitle first is:" +
				// articletitleFirst);
				article.setTitle(articletitleFirst);
			}
			Elements articlelink = ele.select("link");// 非必需节点
			if (CollectionUtils.isEmpty(articlelink)) {
				// logger.warn("rss->channel->item->link is empty.");
			} else {
				// logger.debug("articlelink size is:" +
				// articlelink.size());
				String articlelinkFirst = articlelink.first().text();
				// logger.debug("articlelink first is:" + articlelinkFirst);
				article.setLink(articlelinkFirst);
			}
			Elements articlesource = ele.select("source");// 非必需节点
			if (CollectionUtils.isEmpty(articlesource)) {
				// logger.warn("rss->channel->item->source is empty.");
			} else {
				// logger.debug("articlesource size is:" +
				// articlesource.size());
				String articlesourceFirst = articlesource.first().text();
				// logger.debug("articlesource first is:" +
				// articlesourceFirst);
				article.setSource(articlesourceFirst);
			}
			Elements articleauthor = ele.select("author");// 非必需节点
			if (CollectionUtils.isEmpty(articleauthor)) {
				// logger.warn("rss->channel->item->author is empty.");
			} else {
				// logger.debug("articleauthor size is:" +
				// articleauthor.size());
				String articleauthorFirst = articleauthor.first().text();
				// logger.debug("articleauthor first is:" +
				// articleauthorFirst);
				article.setAuthor(articleauthorFirst);
			}
			Elements articlecategory = ele.select("category");// 非必需节点
			if (CollectionUtils.isEmpty(articlecategory)) {
				// logger.warn("rss->channel->item->category is empty.");
			} else {
				// logger.debug("articlecategory size is:" +
				// articlecategory.size());
				String articlecategoryFirst = articlecategory.first().text();
				// logger.debug("articlecategory first is:" +
				// articlecategoryFirst);
				article.setCategory(articlecategoryFirst);
			}
			Elements articlecomments = ele.select("comments");// 非必需节点
			if (CollectionUtils.isEmpty(articlecomments)) {
				// logger.warn("rss->channel->item->comments is empty.");
			} else {
				// logger.debug("articlecomments size is:" +
				// articlecomments.size());
				String articlecommentsFirst = articlecomments.first().text();
				// logger.debug("articlecomments first is:" +
				// articlecommentsFirst);
				article.setComments(articlecommentsFirst);
			}
			Elements articledescription = ele.select("description");
			if (CollectionUtils.isEmpty(description)) {
				logger.error("rss->channel->item->description is empty.");
				continue;
			} else {
				// logger.debug("articledescription size is:" +
				// articledescription.size());
				String articledescriptionFirst = articledescription.first().text();
				// logger.debug("articledescription first is:" +
				// articledescriptionFirst);
				article.setDescription(articledescriptionFirst);
			}
			if (articles.size() >= FeedSyncConstant.default_feed_article_new_max) {
				break;
			}
			articles.add(article);
		}
		// logger.debug("articles size is:" + articles.size());
		// if (!CollectionUtils.isEmpty(articles)) {
		feedParseBo.setItem(articles);
		// }
		// logger.debug("finally feed is:" + feed.toString());
		doc = null;
		rss = null;
		return feedParseBo;
	}

	// public static void main(String[] args) {
	// doParseXML(FileUtils.readByChars("E:xml.txt"));
	// doParseXML(FileUtils.readByChars("E:xml3.txt"));
	// }
}
