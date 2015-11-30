package per.rss.core.base.constant;

import java.util.Date;

/**
 * 定义一般的常量，要考虑使用枚举来实现
 * 
 * http://www.cnblogs.com/happyPawpaw/archive/2013/04/09/3009553.html
 * 
 */
public class CommonConstant {
	public final static int LOGINSUCCESSCLIENTCOOIKEEXPIRE = 3 * 24 * 60 * 60;
	/** 系统内部通讯参数，默认字符编码定义 */
	public final static String CharacterEncoding_default = "UTF-8";
	/** 系统内部通讯参数，默认MIME定义 */
	public final static String ContentType_default = "text/html;charset=" + CharacterEncoding_default;
	/** 系统内部通讯参数，json MIME定义 */
	public final static String ContentType_JSON = "application/json;charset=" + CharacterEncoding_default;

	public final static String CSRFTOKENNAME = "verifToken";
	/** 系统内部通讯参数，登陆成功，客户端cookie名称 */
	public final static String LOGINSUCCESSCLIENTCOOIKENAME = "__RSSAPISAFETYSSOLOGINCOOIKE";
	/** 系统内部通讯参数，运行时容器默认cookie名称 */
	public final static String DEFAULTCOOIKENAME = "JSESSIONID";
	/** 系统内部通讯参数，时间日期格式定义 */
	public final static String DatetimePattern_Full = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 系统内部通讯参数，时间日期中国时区定义 */
	public final static String DatetimeTimeZone_Default = "GMT+8";

	public final static Date dateBegining = new Date(0l);
	public final static long longBegining = -1l;
	public final static int intBegining = -1;
	public final static String stringBegining = "";

	public static enum status {
		// 利用构造函数传参
		success(1), failed(0);
		// 定义私有变量
		private int code;

		// 构造函数，枚举类型只能为私有
		private status(int code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}

		public int getCode() {
			return this.code;
		}
	};

	public static enum response {
		// 利用构造函数传参
		success(1, "成功"), failed(0, "失败");
		// 定义私有变量
		private int code;
		private String desc;

		// 构造函数，枚举类型只能为私有
		private response(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return String.valueOf(this.name() + ":" + this.code + "," + this.desc);
		}

		public int getCode() {
			return this.code;
		}

		public String getDesc() {
			return this.desc;
		}
	};
}
