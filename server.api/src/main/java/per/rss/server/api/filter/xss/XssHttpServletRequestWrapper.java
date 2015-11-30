package per.rss.server.api.filter.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.util.StringUtils;
import per.rss.server.api.bo.core.req.Req;
import per.rss.server.api.constant.InterfaceConstant;

/**
 * 
 * 顺序1：传输密文数据进行解密
 * 
 * 顺序2：预防SQL注入攻击、预防XSS攻击
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private static final Logger logger = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			String value = values[i];
			value = decryptRequestData(parameter, value);
			encodedValues[i] = cleanXSS(value);
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		value = decryptRequestData(parameter, value);
		return cleanXSS(value);
	}

	/**
	 * 数据传输，请求的数据进行解密
	 * 
	 * @param parameterKey
	 * @param value
	 * @return
	 */
	private String decryptRequestData(String parameterKey, String value) {
		logger.debug("decryptRequestData is start.");
		if (StringUtils.isEmpty(parameterKey)) {
			logger.error("parameterKey is empty.");
			return null;
		}
		if (!parameterKey.equals(InterfaceConstant.parameterKeyCiphertext)) {
			logger.debug("parameterKey is not " + InterfaceConstant.parameterKeyCiphertext);
			return null;
		}
		Req req = null;
		try {
			req = StringUtils.formatJson(value, Req.class);
		} catch (Exception e) {
			logger.error("decryptRequestData is exception.", e);
			req = null;
		}
		if (req == null) {
			logger.error("req is empty.");
			return null;
		}
		req = this.decryptData(req);
		if (req != null) {
			return StringUtils.toJSONString(req);
		}
		return null;
	}

	private Req decryptData(Req req) {
		return req;
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		// You'll need to remove the spaces from the html entities below
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}
}