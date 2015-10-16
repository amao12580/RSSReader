package per.rss.server.api.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.rss.core.base.constant.CommonConstant;
import per.rss.server.api.biz.user.login.LoginBiz;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.bo.core.Resp;
import per.rss.server.api.bo.user.login.LoginBo;
import per.rss.server.api.controller.base.BaseController;

@Controller
@RequestMapping(value = "/login/")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired(required = true)
	private LoginBiz loginBiz;

	// 解决乱码资料：
	// 1.http://tchen8.iteye.com/blog/993504
	// 2.http://my.oschina.net/u/140421/blog/176625
	@RequestMapping(value = "/do", method = RequestMethod.POST, produces = CommonConstant.ContentType_JSON)
	@ResponseBody
	public Resp POSTLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			LoginBo loginBo) {
		logger.debug("POSTLogin is start.");
		if (!isValidCsrfHeaderToken(session, request)) {
			return new Resp(Error.message.submit_error);
		}
		return loginBiz.doUserLogin(loginBo, request, response, session);
	}

	@RequestMapping(value = "/do", method = RequestMethod.OPTIONS, produces = CommonConstant.ContentType_JSON)
	@ResponseBody
	public Resp OPTIONSLogin(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("OPTIONSLogin is start.");
		return new Resp();
	}

	// 前后台使用rsa加解密：http://www.blogjava.net/wangxinsh55/archive/2015/05/19/425175.html
	@RequestMapping(value = "/to", method = RequestMethod.GET, produces = CommonConstant.ContentType_JSON)
	@ResponseBody
	public Resp toLogin(HttpSession session, HttpServletRequest request) {
		logger.debug("to is start.");
		return loginBiz.toLogin(session);
	}
}
