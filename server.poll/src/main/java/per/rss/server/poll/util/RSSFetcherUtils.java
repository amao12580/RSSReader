package per.rss.server.poll.util;

import java.io.IOException;
import java.util.Date;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.model.internet.Proxy;
import per.rss.core.base.model.log.LogFeedRequest;
import per.rss.core.base.util.HttpClientUtils;
import per.rss.core.base.util.StringUtils;
import per.rss.core.base.util.UUIDUtils;
import per.rss.server.poll.model.log.LogFeedFetcher;
import per.rss.server.poll.model.log.LogFeedParser;
import per.rss.server.poll.util.xml.JsoupXMLHandler;
import per.rss.server.poll.util.xml.XMLHandler;

public class RSSFetcherUtils {
	private static final Logger logger = LoggerFactory.getLogger(RSSFetcherUtils.class);

	public static void main(String[] args) throws IOException, DocumentException {
		String spec = "http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss&sub=0]http://news.baidu.com/n?cmd=1&class=civilnews&tn=rss&sub=0";
		String spec1 = "http://rss.sina.com.cn/ent/hot_roll.xml";
		String spec2 = "http://hanhanone.sinaapp.com/feed/dajia";
		String spec3 = "http://feeds2.feedburner.com/cnbeta-full";
		String spec4 = "http://sinacn.weibodangan.com/user/1850988623/rss/";
		// doFetch(spec);
		// doFetch(spec1);
		// doFetch(spec2);
		logger.debug("result:"+StringUtils.toJSONString(doFetch(spec3)));
		// doFetch(spec4);
	}

	/**
	 * @param urlStr
	 *            rss网络地址
	 * @throws IOException
	 */
	protected static LogFeedFetcher doFetch(String urlStr) throws IOException {
		return doFetch(urlStr, null,null);
	}

	/**
	 * @param urlStr
	 *            rss网络地址
	 * @param proxy
	 *            抓取时使用代理链接
	 * @throws IOException
	 *             当reader变量没有正确关闭时，抛出该异常
	 */
	@SuppressWarnings("static-access")
	private static LogFeedFetcher doFetch(String urlStr, Proxy proxy,String response_charsets){
		LogFeedFetcher logFeedFetcher = new LogFeedFetcher();
		logFeedFetcher.setId(UUIDUtils.randomUUID());
		logFeedFetcher.setFetchStartDate(new Date());
		LogFeedRequest logFeedRequest = HttpClientUtils.doHttpGetRequest(urlStr,proxy,response_charsets);
		if (logFeedRequest != null) {
			logFeedFetcher.setLogFeedRequest(logFeedRequest);
			String responseXml = logFeedRequest.getResponseHtml();
			boolean verify = false;
			XMLHandler handler = new JsoupXMLHandler();
			verify = handler.validator(responseXml);
			if (!verify) {
				logger.error("responseXml verify is error.");
				return null;
			}
			LogFeedParser logFeedParser = handler.parse(responseXml);
			if(logFeedParser!=null){
				logFeedFetcher.setLogFeedParser(logFeedParser);
			}
		}
		logFeedFetcher.setFetchEndDate(new Date());
		long takeTime=logFeedFetcher.getFetchEndDate().getTime()-logFeedFetcher.getFetchStartDate().getTime();
		logFeedFetcher.setTakeTime(takeTime);
		logger.debug("all time is:"+takeTime);
		return logFeedFetcher;
	}
}