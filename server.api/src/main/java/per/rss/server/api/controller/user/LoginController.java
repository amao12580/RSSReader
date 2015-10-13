package per.rss.server.api.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.rss.core.base.util.StringUtils;
import per.rss.server.api.biz.user.login.LoginBiz;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.bo.core.Resp;
import per.rss.server.api.bo.user.login.LoginBo;
import per.rss.server.api.controller.base.BaseController;

@Controller
@RequestMapping(value = "/user/")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired(required = true)
	private LoginBiz loginBiz;

	// 解决乱码资料：
	// 1.http://tchen8.iteye.com/blog/993504
	// 2.http://my.oschina.net/u/140421/blog/176625

	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login(HttpSession session, HttpServletRequest request, LoginBo loginBo, String doUserLoginCallBack) {
		logger.debug("login is start.");
		logger.debug("doUserLoginSuccessCallBack:" + doUserLoginCallBack);
		Resp result = null;
		boolean validSubmit = isValidCsrfHeaderToken(session, request);
		if (!validSubmit) {
			result = new Resp(Error.message.submit_error);
		} else {
			if (StringUtils.isEmpty(doUserLoginCallBack)) {
				result = new Resp(Error.message.system_error);
			} else {
				result = loginBiz.doUserLogin(loginBo);
			}
		}
		return toCallBackResponse(doUserLoginCallBack, result);
	}

	// 前后台使用rsa加解密：http://www.blogjava.net/wangxinsh55/archive/2015/05/19/425175.html
	@RequestMapping(value = "/toLogin", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getRSAKey(HttpSession session, String toSecurityLoginCallBack) {
		logger.debug("getRSAKey is start.");
		logger.debug("toSecurityLoginCallBack:" + toSecurityLoginCallBack);
		Resp result = null;
		if (StringUtils.isEmpty(toSecurityLoginCallBack)) {
			result = new Resp(Error.message.system_error);
		} else {
			result = loginBiz.getRSAKey(session);
		}
		return toCallBackResponse(toSecurityLoginCallBack, result);
	}
}
