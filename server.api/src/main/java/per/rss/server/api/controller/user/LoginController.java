package per.rss.server.api.controller.user;

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

@Controller
@RequestMapping(value = "/user/")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	// private static final String callBackFunction =
	// "doUserLoginSuccessCallBack";

	@Autowired(required = true)
	private LoginBiz loginBiz;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String login(LoginBo loginBo, String doUserLoginSuccessCallBack) {
		logger.debug("login is start.");
		logger.debug("doUserLoginSuccessCallBack:" + doUserLoginSuccessCallBack);
		Resp result = null;
		if (StringUtils.isEmpty(doUserLoginSuccessCallBack)) {
			result = new Resp(Error.message.system_error);
		} else {
			result = loginBiz.doUserLogin(loginBo);
		}
		String finalResult = doUserLoginSuccessCallBack + "(" + StringUtils.toJSONString(result) + ")";
		logger.debug("finalResult:" + finalResult);
		return finalResult;
	}
}
