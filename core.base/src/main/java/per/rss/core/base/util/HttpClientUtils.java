package per.rss.core.base.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.bo.internet.ProxyBo;
import per.rss.core.base.bo.log.LogFeedFetcherBo;
import per.rss.core.base.constant.CommonConstant;

public class HttpClientUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
			.setConnectTimeout(5000).build();// 设置请求和传输超时时间

	/**
	 * 还没有实现使用代理访问
	 * 
	 * @param urlStr
	 * @param proxy_ip
	 * @param proxy_port
	 * @param proxy_username
	 * @param proxy_password
	 * @param response_charsets
	 *            设定响应内容的字符集：gbk、utf-8，默认是gbk.
	 * @return
	 */
	public static LogFeedFetcherBo doHttpGetRequest(String urlStr, ProxyBo proxy, String response_charsets) {
		LogFeedFetcherBo logFeedRequest = new LogFeedFetcherBo();
		logFeedRequest.setRequestStartDate(new Date());
		String html = null;
		CloseableHttpClient httpclient = null;
		HttpGet httpget = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			// 创建httpget.
			httpget = new HttpGet(urlStr);
			// 设置httpget超时信息
			httpget.setConfig(requestConfig);
			// 設置httpGet的头部參數信息
			httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpget.setHeader("Accept-Charset", "GB2312,GBK,utf-8;q=0.7,*;q=0.7");
			httpget.setHeader("Accept-Encoding", "gzip, deflate");
			httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
			httpget.setHeader("Connection", "keep-alive");
			httpget.setHeader("Cache-Control", "max-age=0");
			// httpget.setHeader("Cookie", "123456");
			httpget.setHeader("Content-Type", "text/html");
			httpget.setHeader("refer", "https://www.baidu.com/");
			httpget.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36");
			// httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1;
			// rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			// 执行get请求.
			response = httpclient.execute(httpget);
			if (response == null) {
				logger.error("doHttpGetRequest is error,response is null.");
				logFeedRequest.setRequestStatus(CommonConstant.status_failed);
			} else {
				logFeedRequest.setRequestStatus(CommonConstant.status_success);
				// 响应状态
				StatusLine sl = response.getStatusLine();
				if (sl == null) {
					logFeedRequest.setResponseStatus(CommonConstant.status_failed);
				} else {
					int code = sl.getStatusCode();
					logFeedRequest.setResponseCode(code);
					if (code != 200) {
						logFeedRequest.setResponseStatus(CommonConstant.status_failed);
					} else {
						logFeedRequest.setResponseStatus(CommonConstant.status_success);
					}
					// 获取响应实体
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						// 响应内容
						if (StringUtils.isEmpty(response_charsets)) {
							response_charsets = "gbk";
						}
						html = EntityUtils.toString(entity, Charset.forName(response_charsets));
						if (!StringUtils.isEmpty(html)) {
							logFeedRequest.setHtmlLength(html.length());
							logFeedRequest.setResponseHtml(html);
						}
					}
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("doHttpGetRequest is error,ClientProtocolException.", e);
			logFeedRequest = logFeedRequest.doException(logFeedRequest, e);
		} catch (ParseException e) {
			logger.error("doHttpGetRequest is error,ParseException.", e);
			logFeedRequest = logFeedRequest.doException(logFeedRequest, e);
		} catch (IOException e) {
			logger.error("doHttpGetRequest is error,IOException.", e);
			logFeedRequest = logFeedRequest.doException(logFeedRequest, e);
		} finally {
			// 关闭连接,释放资源
			httpget = null;
			try {
				if (httpclient != null) {
					httpclient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error("close is error,IOException.", e);
				httpclient = null;
				response = null;
			}
		}
		logFeedRequest.setRequestEndDate(new Date());
		long takeTime = logFeedRequest.getRequestEndDate().getTime() - logFeedRequest.getRequestStartDate().getTime();
		logFeedRequest.setTakeTime(takeTime);
		logger.debug("internet time is:"+takeTime+"ms");
		return logFeedRequest;
	}
}
