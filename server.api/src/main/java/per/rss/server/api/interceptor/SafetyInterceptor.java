package per.rss.server.api.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import per.rss.core.base.util.StringUtils;
import per.rss.server.api.bo.core.Browser;
import per.rss.server.api.init.Config;
import per.rss.server.api.util.BrowserUtils;
import per.rss.server.api.util.IPUtils;

/**
 * 
 * 安全方面的拦截
 * 
 * preHandle调用controller具体方法之前调用，
 * 
 * postHandle完成具体方法之后调用，
 * 
 * afterCompletion完成对页面的render以后调用
 *
 */
@Controller
public class SafetyInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(SafetyInterceptor.class);

	// 需要把SafetyInterceptor放在拦截器链的第一个，这样得到的时间才是比较准确的。
	// private NamedThreadLocal<Long> startTimeThreadLocal = new
	// NamedThreadLocal<Long>("Safety-StartTime");

	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在
	 * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，
	 * 而且所有的Interceptor中的preHandle方法都会在
	 * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，
	 * 这种中断方式是令preHandle的返 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("preHandle.");
		// 判断浏览器类型和版本 开始
		Browser b = BrowserUtils.getUserAgent(request);
		logger.debug("BorwerUtils.getUserAgent：" + StringUtils.toJSONString(b));
		if (!BrowserUtils.checkSupport(b)) {
			logger.error("Unable to support the browser.");
			return false;
		}
		// 判断浏览器类型和版本 结束

		// 判断IP是否需要锁定 开始
		logger.debug("ip:" + IPUtils.getRemoteIp(request));
		// 判断IP是否需要锁定 结束

		// 判断CORS安全 开始
		String sourceOrigin = request.getHeader("Origin");
		String sourceReferer = request.getHeader("referer");
		String reqMethod = request.getMethod();
		logger.debug("sourceReferer:" + sourceReferer);
		if (StringUtils.isEmpty(reqMethod)) {
			return false;
		}
		logger.debug("sourceReferer:" + sourceReferer);
		if (StringUtils.isEmpty(sourceOrigin)) {
			return false;
		}
		logger.debug("Origin:" + sourceOrigin);
		String configOrigin = Config.portal_webpage_origin;
		if (StringUtils.isEmpty(configOrigin)) {
			return false;
		}
		logger.debug("Config:" + configOrigin);
		if (!configOrigin.equals(sourceOrigin)) {
			return false;
		}
		setAllowCORS(response, configOrigin);// 这一句要放在最后return之前
		// 判断CORS安全 结束

		return true;// 放行
	}

	/**
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，
	 * 它的执行时间是在处理器进行处理之
	 * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，
	 * 也就是说在这个方法中你可以对ModelAndView进行操
	 * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，
	 * 这跟Struts2里面的拦截器的执行过程有点像，
	 * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，
	 * Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
	 * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，
	 * 要在Interceptor之后调用的内容都写在调用invoke方法之后。
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("postHandle.");
		if (modelAndView != null) {
			Map<String, Object> model = modelAndView.getModel(); // 这里可以遍历执行完control之后，control方法返回的ModelAndView对象，并且可以修改里面的内容
			logger.debug("model size is " + model.size());
			if (model.size() > 0) {
				for (Iterator it = model.entrySet().iterator(); it.hasNext();) {
					logger.debug(it.next() + "\t\t-----------------------------------");
				}
			}
		}
	}

	private void setAllowCORS(HttpServletResponse response, String configDomain) {
		// https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS
		response.addHeader("Access-Control-Allow-Origin", configDomain);
		response.addHeader("Access-Control-Allow-Methods", "POST");
		// response.addHeader("Access-Control-Allow-Headers",
		// "Origin,X-ITBILU,X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept");
		response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Allow", "POST");
	}

	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，
	 * 也就是DispatcherServlet渲染了视图执行，
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// long endTime = System.currentTimeMillis();// 2、结束时间
		// long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
		// long consumeTime = endTime - beginTime;// 3、消耗的时间
		// if (consumeTime > 500) {// 此处认为处理时间超过500毫秒的请求为慢请求
		// // TODO 记录到日志文件
		// System.out.println(String.format("%s consume %d millis",
		// request.getRequestURI(), consumeTime));
		// }
	}
}
