package per.rss.portal.webpage.controller.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/test/")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value="/case1", method = RequestMethod.GET)
	@ResponseBody
	public String case1(){
		logger.debug("case1");
		return "case1 is ok.";
	}
	
	@RequestMapping(value="/case2",method = RequestMethod.GET)
	public String service(){
		logger.debug("case2");
		return "/test/case2";
	}
}
