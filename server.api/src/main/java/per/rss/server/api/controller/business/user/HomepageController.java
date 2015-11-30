package per.rss.server.api.controller.business.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.rss.core.base.constant.CommonConstant;
import per.rss.server.api.bo.core.resp.Resp;
import per.rss.server.api.constant.InterfaceConstant;
import per.rss.server.api.controller.base.BusinessController;

@Controller
@RequestMapping(value = InterfaceConstant.type_business
		+ InterfaceConstant.module_homepage, method = RequestMethod.GET, produces = CommonConstant.ContentType_JSON)
@ResponseBody
public class HomepageController extends BusinessController {
	private static final Logger logger = LoggerFactory.getLogger(HomepageController.class);

	@RequestMapping("11")
	public Resp A(String params) {
		logger.debug("A is start.params:" + params);
		return prepareResponseData(new Resp());
	}

	@RequestMapping("12")
	public Resp B(String params) {
		logger.debug("B is start.params:" + params);
		return prepareResponseData(new Resp());
	}

	@RequestMapping("13")
	public Resp C(String params) {
		logger.debug("C is start.params:" + params);
		return prepareResponseData(new Resp());
	}

	@Override
	public Resp doResponseData(final Resp finalData) {
		logger.debug("doResponseData is start.");
		return finalData;
	}
}
