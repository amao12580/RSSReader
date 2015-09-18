package per.rss.core.base.util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
			.setConnectTimeout(5000).build();// 设置请求和传输超时时间

	/**
	 * @param urlStr
	 * @param proxy_ip
	 * @param proxy_port
	 * @param proxy_username
	 * @param proxy_password
	 * @return
	 */
	public static String doHttpGetRequest(String urlStr, String proxy_ip, Integer proxy_port, String proxy_username,
			String proxy_password) {
		return doHttpGetRequest(urlStr, proxy_ip, proxy_port, proxy_username, proxy_password, "");
	}

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
	public static String doHttpGetRequest(String urlStr, String proxy_ip, Integer proxy_port, String proxy_username,
			String proxy_password, String response_charsets) {
		String html = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			// 创建httpget.
			HttpGet httpget = new HttpGet(urlStr);
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
				return null;
			}
			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 响应状态
			logger.debug("StatusLine:" + response.getStatusLine());
			// 获取响应内容的字符集
			ContentType contentType = ContentType.getOrDefault(entity);
			Charset charset = contentType.getCharset();
			if (charset != null) {
				logger.debug("respose charset:" + charset.displayName() + "," + charset.toString());
			} else {
				logger.warn("respose charset is null.");
			}
			if (entity != null) {
				// 响应内容
				if (StringUtils.isEmpty(response_charsets)) {
					response_charsets = "gbk";
				}
				html = EntityUtils.toString(entity, Charset.forName(response_charsets));

				// html = EntityUtils.toString(entity,
				// Charset.forName("utf-8"));
				// html = EntityUtils.toString(entity, StandardCharsets.UTF_8);
				// 响应内容长度
				// logger.debug("Response content length: " +
				// entity.getContentLength());
				logger.debug("Response content length: " + html.length());
				// logger.debug("Response content :" + html);
			}
			httpget = null;
		} catch (ClientProtocolException e) {
			logger.error("doHttpGetRequest is error,ClientProtocolException.", e);
			return null;
		} catch (ParseException e) {
			logger.error("doHttpGetRequest is error,ParseException.", e);
			return null;
		} catch (IOException e) {
			logger.error("doHttpGetRequest is error,IOException.", e);
			return null;
		} finally {
			// 关闭连接,释放资源
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
		return html;

	}
}
