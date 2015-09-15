package per.rss.portal.webpage.controller.test;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.rss.core.base.util.MyLogger;

@Controller
@RequestMapping(value="/test/")
public class TestController {
	private static final Logger logger = MyLogger.getLogger(TestController.class);
	
	@RequestMapping(value="/case1", method = RequestMethod.GET)
	@ResponseBody
	public String case1(){
		logger.debug("case1");
		return "case1 is ok.";
	}
}
