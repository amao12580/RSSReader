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

	public static enum status2 {
		// 利用构造函数传参
		success(1, "成功"), failed(0, "失败");
		// 定义私有变量
		private int code;
		private String desc;

		// 构造函数，枚举类型只能为私有
		private status2(int code, String desc) {
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

	public final static int default_feed_new_max = 20;// 每个订阅源默认最多解析20篇文章

	public static void main(String[] args) {
		System.out.println(CommonConstant.status.failed);
		System.out.println(Integer.valueOf(CommonConstant.status.failed.toString()));
		System.out.println(CommonConstant.status.success);
		System.out.println(CommonConstant.status2.success);
		System.out.println(CommonConstant.status2.success.getCode());
		System.out.println(CommonConstant.status2.success.getDesc());
		System.out.println(CommonConstant.status2.failed.getCode());
	}
}
