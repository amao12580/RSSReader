package per.rss.server.api.bo.core;

import per.rss.core.base.util.StringUtils;

public class Error {
	public static enum message {// 定义rss抓取的时间间隔
		// 利用构造函数传参
		common_success(0, "操作成功"), common_failed(1, "操作失败"), system_error(3, "系统错误"), system_exception(4,
				"系统异常"), input_error(5, "输入错误"), submit_error(6, "非法请求，不允许提交"),

		user_login_username_isEmpty(1000, "用户名不允许为空"), user_login_password_isEmpty(1001,
				"密码不允许为空"), user_login_usernameOrPassword_notMatch(1002, "用户名或密码错误"),user_login_token_error(1003, "用户当前token失效");

		private int code;
		private String desc;

		// 构造函数，枚举类型只能为私有
		private message(int code, String desc) {
			if (!StringUtils.isEmpty(desc)) {
				if ((!(desc.endsWith("!") || desc.endsWith("！"))) || (!(desc.endsWith(".") || desc.endsWith("。")))) {
					desc += "!";
				}
			}
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}

		@Override
		public String toString() {
			return StringUtils.toJSONString(this);
		}
	}
}
