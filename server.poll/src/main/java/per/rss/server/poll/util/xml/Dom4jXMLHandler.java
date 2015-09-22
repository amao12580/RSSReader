package per.rss.server.poll.util.xml;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.util.FileUtils;
import per.rss.server.poll.model.feed.Feed;

/**
 * 传统方案，作用备用
 * 
 * 代码还需要完善
 * 
 * 正常的xml可以解析，节点中含有"content:encoded"时会报错
 * 
 * @author cifpay
 *
 */
public class Dom4jXMLHandler extends XMLHandler {

	private static final Logger logger = LoggerFactory.getLogger(RomeXMLHandler.class);
	
	protected Dom4jXMLHandler() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Feed doParseXML(String xml) throws Exception {
		Feed feed=null;
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
		return feed;
	}

	public static void main(String[] args) {
		XMLHandler h=new Dom4jXMLHandler();
		h.parse(FileUtils.readByChars("E:xml3.txt"));
		h.parse(FileUtils.readByChars("E:xml2.txt"));
		h.parse(FileUtils.readByChars("E:xml.txt"));
	}
}

