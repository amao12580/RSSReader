package per.rss.server.poll.util.xml;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.DateParseUtils;
import per.rss.core.base.util.DateTimeUtils;
import per.rss.core.base.util.StringUtils;
import per.rss.server.poll.bo.feed.FeedParseBo;
import per.rss.server.poll.bo.feed.LogFeedParserBo;
import per.rss.server.poll.util.xml.impl.JsoupXMLHandler;

public abstract class XMLHandler {
	private static final Logger logger = LoggerFactory.getLogger(XMLHandler.class);
	// 类加载时就初始化
	private static final XMLHandler instance = new JsoupXMLHandler();

	protected XMLHandler() {
	}

	public final static XMLHandler getInstance() {
		return instance;
	}

	public final static boolean validator(String xml) {
		boolean result = false;
		if (StringUtils.isEmpty(xml)) {
			logger.error("xml is empty.");
			return false;
		}
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

	public final LogFeedParserBo parse(String parseId, String feedId, Date lastedSyncDate, String xml) {
		LogFeedParserBo logFeedParser = new LogFeedParserBo();
		logFeedParser.setId(parseId);
		logFeedParser.setParseStartDate(new Date());
		logFeedParser.setContent(xml);
		FeedParseBo feedParseBo = null;
		try {
			logFeedParser.setStatus(CommonConstant.status.failed.getCode());
			if (StringUtils.isEmpty(xml)) {
				logger.error("xml is empty.");
				feedParseBo = null;
			} else {
				feedParseBo = doParseXML(feedId, lastedSyncDate, xml);
				if (feedParseBo != null) {
					logFeedParser.setStatus(CommonConstant.status.success.getCode());
				}
			}
		} catch (Exception e) {
			logger.error("parse is error.", e);
			feedParseBo = null;
			logFeedParser.setStatus(CommonConstant.status.failed.getCode());
			logFeedParser.setErrorMessage(e.getMessage());
		}
		logFeedParser.setFeedParseBo(feedParseBo);
		logFeedParser.setParseEndDate(new Date());
		long takeTime = logFeedParser.getParseEndDate().getTime() - logFeedParser.getParseStartDate().getTime();
		logFeedParser.setTakeTime(takeTime);
		logger.debug("parse time is:" + takeTime);
		return logFeedParser;
	}

	/**
	 * 继承式多态
	 * 
	 * @param feedSyncBo
	 * 
	 * @param xml
	 * @return
	 */
	abstract protected FeedParseBo doParseXML(String feedId, Date lastedSyncDate, String xml) throws Exception;

	private final static String defaultTimezone1 = "GMT";
	private final static String defaultTimezone2 = "+0800";

	protected final static Date parsePubDateString(String pubDateString) {
		String pubDateStr = pubDateString;

		if (StringUtils.isEmpty(pubDateStr)) {
			return null;
		}
		if (pubDateStr.contains("CDATA") || pubDateStr.contains("cdata")) {
			return null;
		}
		Date resultDate = null;
		resultDate = DateParseUtils.parseDate(pubDateStr, Locale.US);
		if (resultDate != null) {
			return resultDate;
		}
		resultDate = parseDateByTimezone(pubDateStr, defaultTimezone1);
		if (resultDate != null) {
			return resultDate;
		}
		resultDate = parseDateByTimezone(pubDateStr, defaultTimezone2);
		if (resultDate != null) {
			return resultDate;
		}
		return resultDate;
	}

	private final static Date parseDateByTimezone(String pubDateStr, String timezone) {
		// logger.debug("parseDateByTimezone,pubDateStr:" + pubDateStr +
		// ",timezone:" + timezone);
		Date resultDate = null;
		if (!pubDateStr.endsWith(timezone)) {
			pubDateStr += " " + timezone;
			resultDate = DateParseUtils.parseDate(pubDateStr, Locale.US);
			if (resultDate != null) {
				// logger.debug("parseDateByTimezone:" + resultDate.getTime());
				return resultDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static void main(String[] args) throws ParseException {
		String dateStr = "Tue, 29 Sep 2015 14:00:51";
		Date date = parsePubDateString(dateStr);
		System.out.println(date.getTime());
		System.out.println(DateTimeUtils.formatDateTime(date));
	}
}