package per.rss.server.api.controller.user;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.rss.core.base.constant.CommonConstant;
import per.rss.server.api.bo.core.Resp;
import per.rss.server.api.controller.base.BaseController;

@Controller
@RequestMapping(value = "/homepage/")
public class HomepageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(HomepageController.class);

	@RequestMapping(value = "/to", method = RequestMethod.GET, produces = CommonConstant.ContentType_JSON)
	@ResponseBody
	public Resp to(HttpServletResponse response) {
		logger.debug("to is start.");
		return new Resp();
	}
}
