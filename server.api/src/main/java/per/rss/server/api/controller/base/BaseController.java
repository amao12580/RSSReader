package per.rss.server.api.controller.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;
import per.rss.core.base.util.UUIDUtils;
import per.rss.server.api.bo.core.req.Head;
import per.rss.server.api.bo.core.req.Req;
import per.rss.server.api.bo.core.resp.Resp;
import per.rss.server.api.filter.csrf.CSRFTokenManager;

/**
 * 分发，加解密
 */
public abstract class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	public static void main(String[] args) {
		Head h = new Head();
		h.setActiveId(UUIDUtils.randomUUID());
		h.setCommand(10000);
		h.setMessageId(UUIDUtils.randomUUID());
		h.setSessionId(UUIDUtils.randomUUID());
		h.setSessionSign(UUIDUtils.randomUUID());
		Req r = new Req();
		r.setHead(h);
		r.setBody("我是密文");
		r.setDataMode("我是加密模式");
		r.setDigest("我是数字签名");

		logger.debug(StringUtils.toJSONString(r));
	}

	protected abstract Resp prepareResponseData(Resp data);

	protected abstract Resp doResponseData(final Resp finalData);

	protected boolean isValidCsrfHeaderToken(HttpSession session, HttpServletRequest request) {
		String reqToken = request.getParameter(CommonConstant.CSRFTOKENNAME);
		// String reqToken2 = request.getHeader(CommonConstant.CSRFTOKENNAME);
		// String reqToken3 = request.getContentType();
		// logger.debug("reqToken:" + reqToken);
		Object sessionToken = session.getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
		// logger.debug("sessionToken:" + sessionToken);
		// logger.debug("reqToken3:" + reqToken3);
		if (StringUtils.isEmpty(reqToken)) {
			return false;
		}
		// logger.debug("reqToken:" + reqToken);
		if (sessionToken == null) {
			return false;
		}
		// logger.debug("sessionToken:" + sessionToken.toString());

		if (!reqToken.equals(sessionToken.toString())) {
			return false;
		}
		return true;
	}

	/**
	 * 得到类注解
	 * 
	 * http://www.cnblogs.com/lbangel/p/3523741.html
	 * 
	 * @param clazz
	 *            目标类
	 * @return
	 */
	public String getAnnotation(Class<? extends BaseController> clazz) {
		// 得到类注解
		RequestMapping myClassAnnotation = clazz.getAnnotation(RequestMapping.class);
		printArray(myClassAnnotation.value());
		printArray(myClassAnnotation.method());
		printArray(myClassAnnotation.produces());
		return null;
	}

	/**
	 * 获取方法注解
	 * 
	 * http://www.cnblogs.com/lbangel/p/3523741.html
	 * 
	 * @param clazz
	 *            目标类
	 * @param methodName
	 *            目标方法
	 * @return
	 */
	private static Map<String, String> AnnotationCache = null;

	public String getAnnotation(Class<? extends BaseController> clazz, String methodName) {
		if (clazz == null) {
			return null;
		}
		if (StringUtils.isEmpty(methodName)) {
			return null;
		}
		if (AnnotationCache == null) {
			AnnotationCache = new HashMap<String, String>();
		}
		int hashCode = clazz.hashCode();
		String Key = hashCode + "#" + methodName;
		if (AnnotationCache.containsKey(Key)) {
			return AnnotationCache.get(Key);
		}
		// 获取方法注解
		Method method = null;
		try {
			method = clazz.getMethod(methodName, new Class[] { String.class });
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error("clazz.getMethod is error.", e);
			method = null;
		}
		if (method == null) {
			return null;
		}
		RequestMapping myMethodAnnotation = method.getAnnotation(RequestMapping.class);
		if (myMethodAnnotation == null) {
			return null;
		}
		String[] valueArr = myMethodAnnotation.value();
		if (valueArr == null || valueArr.length <= 0) {
			return null;
		}
		AnnotationCache.put(Key, valueArr[0]);
		return valueArr[0];
	}

	/**
	 * 数组中各个元素进行拼接为一个字符串
	 * 
	 * @param arr
	 * @return
	 */
	private String spliceArray(Object[] arr) {
		String result = "";
		for (int i = 0; i < arr.length; i++) {
			logger.debug("i=" + i + ",value=" + arr[i].toString());
			result += arr[i].toString();
			if (i != arr.length - 1) {
				result += ",";
			}
		}
		return result;
	}

	/**
	 * 遍历数组
	 * 
	 * @param arr
	 */
	private void printArray(Object[] arr) {
		for (int i = 0; i < arr.length; i++) {
			logger.debug("i=" + i + ",value=" + arr[i].toString());
		}
	}
}
