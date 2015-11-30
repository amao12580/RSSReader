package per.rss.server.api.controller.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import per.rss.core.base.util.MD5Utils;
import per.rss.core.base.util.StringUtils;
import per.rss.server.api.bo.core.resp.Resp;
import per.rss.server.api.util.code.impl.Base64;

public abstract class BusinessController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BusinessController.class);

	public Resp prepareResponseData(Resp data) {
		logger.debug("需要加密响应的数据...");
		Resp finalData = this.encryptResponseData(data);
		return doResponseData(finalData);
	}

	private final Resp encryptResponseData(Resp data) {
		logger.debug("正在加密，数据长度:");
		return data;
	}

	public static void main(String[] args) {
		String sql = "select 用户名 from 用户";
		String encodeStr = null;
		encodeStr = Base64.encrypt(sql);
		System.out.println(encodeStr);
		String sql2 = null;
		sql2 = Base64.decrypt("AA==");
		System.out.println("sql2:"+sql2);
		
		String data="";
		String JSONStr=StringUtils.toJSONString(data);
		System.out.println("JSONStr:"+JSONStr);
		String data2=StringUtils.formatJson(JSONStr, String.class);
		System.out.println("data2:"+data2);
		if(StringUtils.isEmpty(data2)){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
		System.out.println(MD5Utils.code("123582adsf234但是对方水电费胜多负少ASDERsdf!@#@sdfsd"));
	}

	public abstract Resp doResponseData(final Resp finalData);
}
