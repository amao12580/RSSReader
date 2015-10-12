package per.rss.server.api.bo.user.login;

import per.rss.core.base.constant.CommonConstant;
import per.rss.core.base.util.StringUtils;

public class LoginBo {
	private String username = CommonConstant.stringBegining;
	private String password = CommonConstant.stringBegining;
//	private String callBackFunction = CommonConstant.stringBegining;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public String getCallBackFunction() {
//		return callBackFunction;
//	}
//
//	public void setCallBackFunction(String callBackFunction) {
//		this.callBackFunction = callBackFunction;
//	}

	@Override
	public String toString() {
		// String resultJSONStr = StringUtils.toJSONString(result);
		// String callback = this.getCallBackFunction();// 客户端请求参数
		// return callback + "(" + resultJSONStr + ")";// 返回jsonp格式数据
		return StringUtils.toJSONString(this);
	}
}
