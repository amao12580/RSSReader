package per.rss.server.api.controller.base;

import per.rss.core.base.util.StringUtils;

public class BaseController {
	public String toCallBackResponse(String callBackFunctionName, Object params) {
		return callBackFunctionName + "(" + StringUtils.toJSONString(params) + ")";
	}
}
