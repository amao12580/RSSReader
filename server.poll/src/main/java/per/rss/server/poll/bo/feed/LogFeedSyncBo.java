package per.rss.server.poll.bo.feed;

import java.util.Date;

import per.rss.core.base.bo.log.LogFeedFetcherBo;
import per.rss.core.base.constant.CommonConstant;

/**
 * 抓取是指：获取网络rss信息+解析xml
 *
 */

public class LogFeedSyncBo {

	private String id = "";
	private String feedId = "";
	private Date fetchStartDate = CommonConstant.dateBegining;// 系统开始抓取的时间
	private Date fetchEndDate = CommonConstant.dateBegining;// 系统完成抓取的时间
	private LogFeedFetcherBo logFeedFetcherBo;// 网络请求的详细信息
	private Long takeTime = -1l;// 尝试解析花费总的时间
	private LogFeedParserBo logFeedParserBo = null;// 解析详情
	private String charsets = "";// 字符集

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFetchStartDate() {
		return fetchStartDate;
	}

	public void setFetchStartDate(Date fetchStartDate) {
		this.fetchStartDate = fetchStartDate;
	}

	public Date getFetchEndDate() {
		return fetchEndDate;
	}

	public void setFetchEndDate(Date fetchEndDate) {
		this.fetchEndDate = fetchEndDate;
	}

	public LogFeedFetcherBo getLogFeedFetcherBo() {
		return logFeedFetcherBo;
	}

	public void setLogFeedFetcherBo(LogFeedFetcherBo logFeedFetcherBo) {
		this.logFeedFetcherBo = logFeedFetcherBo;
	}

	public Long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Long takeTime) {
		this.takeTime = takeTime;
	}

	public LogFeedParserBo getLogFeedParserBo() {
		return logFeedParserBo;
	}

	public void setLogFeedParserBo(LogFeedParserBo logFeedParserBo) {
		this.logFeedParserBo = logFeedParserBo;
	}

	public String getCharsets() {
		return charsets;
	}

	public void setCharsets(String charsets) {
		this.charsets = charsets;
	}

}
