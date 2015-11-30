package per.rss.server.api.controller.business.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.rss.core.base.constant.CommonConstant;
import per.rss.server.api.biz.user.login.LoginBiz;
import per.rss.server.api.bo.core.Error;
import per.rss.server.api.bo.core.resp.Resp;
import per.rss.server.api.bo.user.login.LoginBo;
import per.rss.server.api.constant.InterfaceConstant;
import per.rss.server.api.controller.base.BusinessController;

@Controller
// @RequestMapping(value =
// CommonConstant.interfaceType_business+"{commandType:[1-9]|[1-9]\\d|100}")
// @RequestMapping(value =
// CommonConstant.interfaceType_business+"{commandType:^\\d{1,10}$}")
@RequestMapping(value = InterfaceConstant.type_business
		+ InterfaceConstant.module_login, method = RequestMethod.GET, produces = CommonConstant.ContentType_JSON)
@ResponseBody
public class LoginController extends BusinessController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// @Autowired(required = true)
	private LoginBiz loginBiz;

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("([1-9]|[1-9]\\d|100)");
		for (int i = 1; i < 101; i++) {
			Matcher matcher = pattern.matcher(i + "");
			System.out.println("matcher.matches():" + matcher.matches());
			System.out.println("matcher.groupCount():" + matcher.groupCount());
		}
	}

	@RequestMapping("1")
	public Resp A(String params) {
		logger.debug("A is start.params:" + params);
		return prepareResponseData(new Resp());
	}

	@RequestMapping("2")
	public Resp B(String params) {
		logger.debug("B is start.params:" + params);
		return prepareResponseData(new Resp());
	}

	@RequestMapping("3")
	public Resp C(String params) {
		logger.debug("C is start.params:" + params);
		return prepareResponseData(new Resp());
	}

	@Override
	public Resp doResponseData(final Resp finalData) {
		logger.debug("doResponseData is start.");
		return finalData;
	}

	// 解决乱码资料：
	// 1.http://tchen8.iteye.com/blog/993504
	// 2.http://my.oschina.net/u/140421/blog/176625
	// @RequestMapping(value = "/do", method = RequestMethod.POST, produces =
	// CommonConstant.ContentType_JSON)
	// @ResponseBody
	public Resp POSTLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			LoginBo loginBo) {
		logger.debug("POSTLogin is start.");
		if (!isValidCsrfHeaderToken(session, request)) {
			return new Resp(Error.message.submit_error);
		}
		return loginBiz.doUserLogin(loginBo, request, response, session);
	}

	// @RequestMapping(value = "/do", method = RequestMethod.OPTIONS, produces =
	// CommonConstant.ContentType_JSON)
	// @ResponseBody
	public Resp OPTIONSDoLogin() {
		logger.debug("OPTIONSDoLogin is start.");
		return new Resp();
	}

	// 前后台使用rsa加解密：http://www.blogjava.net/wangxinsh55/archive/2015/05/19/425175.html
	// @RequestMapping(value = "/to", method = RequestMethod.GET, produces =
	// CommonConstant.ContentType_JSON)
	// @ResponseBody
	public Resp toLogin(HttpSession session) {
		logger.debug("to is start.");
		return loginBiz.toLogin(session);
	}
}
