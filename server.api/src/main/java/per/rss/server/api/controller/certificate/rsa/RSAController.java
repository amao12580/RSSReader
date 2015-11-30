package per.rss.server.api.controller.certificate.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import per.rss.core.base.constant.CommonConstant;
import per.rss.server.api.biz.user.rsa.RSABiz;
import per.rss.server.api.bo.core.resp.Resp;
import per.rss.server.api.constant.InterfaceConstant;
import per.rss.server.api.controller.base.AuthPassport;
import per.rss.server.api.controller.base.CertificateController;

@Controller
// @RequestMapping(value = InterfaceConstant.type_certificate
// + InterfaceConstant.module_rsa, method = RequestMethod.GET, produces =
// CommonConstant.ContentType_JSON)
@RequestMapping(method = RequestMethod.POST, produces = CommonConstant.ContentType_JSON)
@ResponseBody
public class RSAController extends CertificateController {
	private static final Logger logger = LoggerFactory.getLogger(RSAController.class);

	private static final String prefix = CertificateController.prefix + InterfaceConstant.module_rsa;

	@Autowired(required = true)
	private RSABiz RSABiz;

	@RequestMapping(RSAController.prefix + InterfaceConstant.code_update)
	@AuthPassport(validate = false)
	public Resp update() {
		logger.debug("update is start.");
		// logger.debug("parameterKeyPlaintext is :" +
		// InterfaceConstant.parameterKeyPlaintext);
		// logger.debug("request.getAttribute is :" +
		// request.getAttribute(InterfaceConstant.parameterKeyPlaintext));
		return prepareResponseData(RSABiz.update());
	}

	@RequestMapping("12")
	public Resp B(String params) {
		logger.debug("B is start.params:" + params);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("methodName:" + methodName);
		logger.debug("getAnnotationValue:" + getAnnotation(RSAController.class, methodName));
		return prepareResponseData(new Resp());
	}

	@RequestMapping("13")
	public Resp C(String params) {
		logger.debug("C is start.params:" + params);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug("methodName:" + methodName);
		logger.debug("getAnnotationValue:" + getAnnotation(RSAController.class, methodName));
		return prepareResponseData(new Resp());
	}

	@Override
	public Resp doResponseData(final Resp finalData) {
		logger.debug("doResponseData is start.");
		return finalData;
	}
}
