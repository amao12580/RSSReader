package per.rss.core.base.constant;

/**
 * 定义一般的常量，要考虑使用枚举来实现
 * 
 * http://www.cnblogs.com/happyPawpaw/archive/2013/04/09/3009553.html
 * 
 * @author cifpay
 *
 */
public class CommonConstant {

	/** 系统内部通讯参数，时间日期格式定义 */
	public final static String DatetimePattern_Full = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 系统内部通讯参数，时间日期中国时区定义 */
	public final static String DatetimeTimeZone_Default = "GMT+8";

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
