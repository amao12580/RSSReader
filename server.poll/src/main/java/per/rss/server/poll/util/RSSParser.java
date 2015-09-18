package per.rss.server.poll.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;

import per.rss.core.base.util.HttpClientUtils;
import per.rss.core.base.util.StringUtils;

public class RSSParser {
	private static final Logger logger = LoggerFactory.getLogger(RSSParser.class);

	public static void main(String[] args) throws IOException, DocumentException {
		String spec = "http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss&sub=0]http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss&sub=0";
		String spec1 = "http://rss.sina.com.cn/ent/hot_roll.xml";
		String spec2 = "http://hanhanone.sinaapp.com/feed/dajia";
		String spec3 = "http://feeds2.feedburner.com/cnbeta-full";
		String spec4 = "http://sinacn.weibodangan.com/user/1850988623/rss/";
		 doParse(spec);
//		 doParse(spec1);
//		 doParse(spec2);
//		 doParse(spec3);
//		 doParse(spec4);
	}

	/**
	 * @param urlStr
	 *            rss网络地址
	 * @throws IOException
	 */
	protected static void doParse(String urlStr) throws IOException {
		doParse(urlStr, null, null, null, null);
	}

	/**
	 * @param urlStr
	 *            rss网络地址
	 * @param proxy
	 *            抓取时使用代理链接
	 * @throws IOException
	 *             当reader变量没有正确关闭时，抛出该异常
	 */
	private static Object doParse(String urlStr, String proxy_ip, Integer proxy_port, String proxy_username,
			String proxy_password) throws IOException {
		long parseStartTime = System.currentTimeMillis();
		logger.debug("开始进行解析：url=" + urlStr);
		String responseXml = HttpClientUtils.doHttpGetRequest(urlStr, proxy_ip, proxy_port, proxy_username, proxy_password);
		if (StringUtils.isEmpty(responseXml)) {
			logger.error("responseXml is null.");
			return null;
		}
		//校验xml的格式
		boolean verify=false;
		verify=XMLHander.validator(responseXml);
		if(!verify){
			logger.error("responseXml 格式不正确，放弃解析.responseXml："+responseXml);
			return null;
		}
		Object result = null;
		try {
			result = doParseByRome(responseXml);
		} catch (Exception e) {
			logger.error("doParseByRome is error.", e);
			result= null;
		}
		if (result == null) {
			try {
				result = doParseByDom4j(responseXml);
			} catch (Exception e) {
				logger.error("doParseByDom4j is error.", e);
				return null;
			}
		}
		if (result == null) {
			logger.error("doParseByDom4j is error.");
		}
		logger.error("result is:" + StringUtils.toJSONString(result));
		long parseEndTime = System.currentTimeMillis();
		logger.debug("解析花费的时间为：" + (parseEndTime - parseStartTime) + "ms.");
		return result;
	}

	

	@SuppressWarnings({ "unchecked" })
	private static Object doParseByDom4j2() throws IOException, DocumentException {
		logger.debug("doParseByDom4j is begin.");
		try{
			SAXReader reader = new SAXReader();  
	        Document document = reader.read(new File("E://xml.txt")); 
			if (document == null) {
				logger.error("document 创建失败.");
				return null;
			}
			Element root = document.getRootElement();// 得到根节点
			if (root == null) {
				logger.error("root 创建失败.");
				return null;
			}
			logger.debug("根节点：" + root.getName()); // 拿到根节点的名称
			
			Element channel = root.element("channel");
			if (channel == null) {
				logger.error("channel 节点不存在.");
				return null;
			}
			List<Element> item2 = root.selectNodes("/rss/channel/item");
			if (item2 == null) {
				return null;
			}
			int itemSum = item2.size();
			if (itemSum == 0) {
				return null;
			}
			logger.debug("item 节点的数量：" + itemSum);
			
			for (Element node : item2) {
				String title = node.elementText("title");
				String description = node.elementText("description");
				String pubDate = node.elementText("pubDate");
				String guid = node.elementText("guid");
				String link = node.elementText("link");
				logger.debug("title：" + title);
				logger.debug("description：" + description);
				logger.debug("pubDate：" + pubDate);
				logger.debug("guid：" + guid);
				logger.debug("link：" + link);
			}
		}catch(Exception e){
			logger.error("RSSParser.doParseByDom4j is error.", e);
			return null;
		}
		logger.debug("doParseByDom4j is end.");
		return "";
	}
	@SuppressWarnings({ "unchecked" })
	private static Object doParseByDom4j(String xml) throws IOException, DocumentException {
		logger.debug("doParseByDom4j is begin.");
		try{
			Document document = DocumentHelper.parseText(xml);
			if (document == null) {
				logger.error("document 创建失败.");
				return null;
			}
			Element root = document.getRootElement();// 得到根节点
			if (root == null) {
				logger.error("root 创建失败.");
				return null;
			}
			logger.debug("根节点：" + root.getName()); // 拿到根节点的名称
			Element channel = root.element("channel");
			if (channel == null) {
				logger.error("channel 节点不存在.");
				return null;
			}
			List<Element> item2 = root.selectNodes("/rss/channel/item");
			if (item2 == null) {
				return null;
			}
			int itemSum = item2.size();
			if (itemSum == 0) {
				return null;
			}
			logger.debug("item 节点的数量：" + itemSum);
			
			for (Element node : item2) {
				String title = node.elementText("title");
				String description = node.elementText("description");
				String pubDate = node.elementText("pubDate");
				String guid = node.elementText("guid");
				String link = node.elementText("link");
				logger.debug("title：" + title);
				logger.debug("description：" + description);
				logger.debug("pubDate：" + pubDate);
				logger.debug("guid：" + guid);
				logger.debug("link：" + link);
			}
		}catch(DocumentException e){
			logger.error("RSSParser.doParseByDom4j is error. xml 格式不正确："+xml, e);
			return null;
		}
		logger.debug("doParseByDom4j is end.");
		return "";
	}

	private static Object doParseByRome(String xml) throws IOException {
		logger.debug("doParseByRome is begin.");
		StringReader sr = null;
		InputSource is = null;
		try {
			sr = new StringReader(xml);
			is = new InputSource(sr);
			SyndFeedInput input = new SyndFeedInput();
			// 得到SyndFeed对象，即得到Rss源里的所有信息
			SyndFeed feed = input.build(is);
			// 得到Rss新闻中子项列表
			List<SyndEntry> entries = feed.getEntries();
			// 循环得到每个子项信息
			for (int i = 0; i < entries.size(); i++) {
				SyndEntry entry = entries.get(i);
				// 标题、连接地址、标题简介、时间是一个Rss源项最基本的组成部分
				logger.debug("标题：" + entry.getTitle());
				logger.debug("连接地址：" + entry.getLink());
				SyndContent description = entry.getDescription();
				String descriptionValue = description.getValue();
				if (!StringUtils.isEmpty(descriptionValue)) {
					descriptionValue = descriptionValue.substring(0, 10) + "...";
				}
				logger.debug("简介model：" + description.getMode());
				logger.debug("简介type：" + description.getType());
				logger.debug("简介value：" + descriptionValue);
				logger.debug("发布时间：" + entry.getPublishedDate());
				// 以下是Rss源可先的几个部分
				logger.debug("标题的作者：" + entry.getAuthor());
//				logger.debug("entry：" + StringUtils.toJSONString(entry));
				// 此标题所属的范畴
				List<SyndCategory> categoryList = entry.getCategories();
				if (categoryList != null) {
					for (int m = 0; m < categoryList.size(); m++) {
						SyndCategory category = (SyndCategory) categoryList.get(m);
						logger.debug("此标题所属的范畴：" + category.getName());
					}
				}
				// 得到流媒体播放文件的信息列表
				List<SyndEnclosure> enclosureList = entry.getEnclosures();
				if (enclosureList != null) {
					for (int n = 0; n < enclosureList.size(); n++) {
						SyndEnclosure enclosure = (SyndEnclosure) enclosureList.get(n);
						logger.debug("流媒体播放文件：" + entry.getEnclosures());
						logger.debug("流媒体播放文件2：" + enclosure.getUrl());
					}
				}
			}
		} catch (Exception e) {
			logger.error("RSSParser.doParseByRome is error.", e);
			return null;
		} finally {
			if (is != null) {
				is = null;
			}
			if (sr != null) {
				sr.close();
			}
		}
		logger.debug("doParseByRome is end.");
		return "";
	}

}