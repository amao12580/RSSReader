package per.rss.server.poll.util.xml;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.model.feed.Feed;
import per.rss.server.poll.model.log.LogFeedParser;

public abstract class XMLHandler {
	private static final Logger logger = LoggerFactory.getLogger(XMLHandler.class);

	public static boolean validator(String xml) {
		boolean result = false;
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
			if (document == null) {
				logger.error("document 对象 创建失败.");
			}
			result = true;
		} catch (DocumentException e) {
			logger.error("validator is error.DocumentException", e);
		} finally {
			if (document != null) {
				document = null;
			}
		}
		return result;
	}

	public LogFeedParser parse(String xml) {
		LogFeedParser logFeedParser = new LogFeedParser();
		logFeedParser.setParseStartDate(new Date());
		// logFeedParser.setParseType(RSSParseConstant.xml_parse_type_jsoup);
		Feed feed = null;
		try {
			if (StringUtils.isEmpty(xml)) {
				logger.error("xml is empty.");
				feed = null;
			} else {
				feed = doParseXML(xml);
			}
			logFeedParser.setStatus(CommonConstant.status_success);
		} catch (Exception e) {
			logger.error("parse is error.", e);
			feed = null;
			logFeedParser.setStatus(CommonConstant.status_failed);
			logFeedParser.setErrorMessage(e.getMessage());
		}
		logFeedParser.setFeed(feed);
		logFeedParser.setParseEndDate(new Date());
		long takeTime = logFeedParser.getParseEndDate().getTime() - logFeedParser.getParseStartDate().getTime();
		logFeedParser.setTakeTime(takeTime);
		logger.debug("parse time is:"+takeTime);
		return logFeedParser;
	}

	/**
	 * 继承式多态
	 * 
	 * @param xml
	 * @return
	 */
	abstract protected Feed doParseXML(String xml) throws Exception;
}
