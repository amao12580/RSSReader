package per.rss.core.base.constant;

import java.util.HashMap;
import java.util.Map;

import per.rss.core.base.util.StringUtils;

public class FeedSyncConstant {

	public static enum speed {// 定义rss抓取的时间间隔
		// 利用构造函数传参
		fastest(0, "0/20 * * * * ?", "最快", "每二十秒一次"), fast(1, "0 0/1 * * * ?", "较快", "每一分钟一次"), common(2,
				"0 0/5 * * * ?", "一般", "每五分钟一次"), medium(3, "0 0/15 * * * ?", "快慢", "每十五分钟一次"), slow(4,
						"0 0/30 * * * ?", "慢速", "每三十分钟一次"), slowest(5, "0 0 0/2 * * ?", "最慢", "每两小时一次");
		private int code;
		private String value;
		private String name;
		private String desc;

		// 构造函数，枚举类型只能为私有
		private speed(int code, String value, String name, String desc) {
			this.code = code;
			this.value = value;
			this.name = name;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return StringUtils.toJSONString(this);
		}

		public int getCode() {
			return this.code;
		}

		public String getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}

		public String getDesc() {
			return this.desc;
		}

	}

	public static Map<Integer, speed> speedMap = null;

	static {
		if (speedMap == null) {
			speedMap = new HashMap<Integer, speed>();
			speed[] arr = speed.values();
			for (int i = 0; i < arr.length; i++) {
				speed s = arr[i];
				int key = s.getCode();
				if (!speedMap.containsKey(key)) {
					speedMap.put(s.getCode(), s);
				}
			}
		}
	}

	public static String getValueByCode(int code) {
		return speedMap.get(code).getValue();
	}

	public final static int default_feed_article_new_max = 50;// 每个订阅源默认最多解析50篇文章
}
