package per.rss.server.api.controller.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.server.api.bo.core.resp.Resp;
import per.rss.server.api.constant.InterfaceConstant;

public abstract class CertificateController extends BaseController {
	
	public  static final String prefix = InterfaceConstant.type_certificate;
	
	private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);

	public Resp prepareResponseData(Resp data) {
		logger.debug("不需要加密响应的数据...");
		return doResponseData(data);
	}

	public abstract Resp doResponseData(final Resp finalData);
}
