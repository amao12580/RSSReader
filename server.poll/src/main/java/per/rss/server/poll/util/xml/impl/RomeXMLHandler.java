package per.rss.server.poll.util.xml.impl;

import java.io.StringReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;

import per.rss.core.base.util.FileUtils;
import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.model.feed.Feed;
import per.rss.server.poll.util.xml.XMLHandler;

/**
 * http://wiki.xby1993.net/doku.php?id=opensourcelearn:rss%E8%A7%A3%E6%9E%90%E5%
 * BA%93
 * 
 * http://rometools.github.io/rome/
 * 
 * xml解析兼容性比较低
 * 
 * 代码还需要完善
 * 
 * 可以解析到非常丰富的数据
 * 
 * 需要参数“xml”在传入前设置好字符集才能有好效果。
 * 
 * @author cifpay
 *
 */
public class RomeXMLHandler extends XMLHandler {

	private static final Logger logger = LoggerFactory.getLogger(RomeXMLHandler.class);

	private RomeXMLHandler() {

	}

	@Override
	protected Feed doParseXML(String xml) throws Exception {
		Feed feed = null;
		StringReader sr = null;
		InputSource is = null;
		try {
			xml = new String(xml.getBytes("utf-8"));
			sr = new StringReader(xml);
			is = new InputSource(sr);
			SyndFeedInput input = new SyndFeedInput();
			// 得到SyndFeed对象，即得到Rss源里的所有信息
			SyndFeed syndFeed = input.build(is);
			// 得到Rss新闻中子项列表
			List<SyndEntry> entries = syndFeed.getEntries();
			// 循环得到每个子项信息
			for (int i = 0; i < entries.size(); i++) {
				SyndEntry entry = entries.get(i);
				// 标题、连接地址、标题简介、时间是一个Rss源项最基本的组成部分
				// logger.debug("标题：" + entry.getTitle());
				// logger.debug("连接地址：" + entry.getLink());
				SyndContent description = entry.getDescription();
				String descriptionValue = description.getValue();
				if (!StringUtils.isEmpty(descriptionValue)) {
					descriptionValue = descriptionValue.substring(0, 10) + "...";
				}
				// logger.debug("简介model：" + description.getMode());
				// logger.debug("简介type：" + description.getType());
				// logger.debug("简介value：" + descriptionValue);
				// logger.debug("发布时间：" + entry.getPublishedDate());
				// 以下是Rss源可先的几个部分
				// logger.debug("标题的作者：" + entry.getAuthor());
				logger.debug("entry：" + StringUtils.toJSONString(entry));
				// 此标题所属的范畴
				List<SyndCategory> categoryList = entry.getCategories();
				if (categoryList != null) {
					for (int m = 0; m < categoryList.size(); m++) {
						// SyndCategory category = (SyndCategory)
						// categoryList.get(m);
						// logger.debug("此标题所属的范畴：" + category.getName());
					}
				}
				// 得到流媒体播放文件的信息列表
				List<SyndEnclosure> enclosureList = entry.getEnclosures();
				if (enclosureList != null) {
					for (int n = 0; n < enclosureList.size(); n++) {
						// SyndEnclosure enclosure = (SyndEnclosure)
						// enclosureList.get(n);
						// logger.debug("流媒体播放文件：" + entry.getEnclosures());
						// logger.debug("流媒体播放文件2：" + enclosure.getUrl());
					}
				}
			}
		} catch (Exception e) {
			logger.error("RSSParser.doParseByRome is error.", e);
			throw e;
		} finally {
			if (is != null) {
				is = null;
			}
			if (sr != null) {
				sr.close();
			}
		}
		return feed;
	}

	public static void main(String[] args) {
		XMLHandler h = new RomeXMLHandler();
		// h.parse(FileUtils.readByChars("E:xml3.txt"));
		// h.parse(FileUtils.readByChars("E:xml2.txt"));
		h.parse(FileUtils.readByChars("E:xml.txt"));
	}
}
