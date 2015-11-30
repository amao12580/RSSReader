package per.rss.server.api.constant;

import per.rss.core.base.util.StringUtils;
import per.rss.core.base.util.UUIDUtils;

/**
 * 交易接口常量
 */
public class InterfaceConstant {
	/**
	 * 通讯参数：key值
	 */
	public static final String parameterKeyCiphertext = "params";

	/**
	 * 通讯参数：key值
	 */
	public final static String parameterKeyPlaintext = UUIDUtils.randomUUID() + System.currentTimeMillis();

	public static enum dataMode {// 定义数据加解密模式
		// 利用构造函数传参
		level_one("1");

		private String model;

		// 构造函数，枚举类型只能为私有
		private dataMode(String model) {
			this.model = model;
		}

		public String getModel() {
			return this.model;
		}

		@Override
		public String toString() {
			return StringUtils.toJSONString(this);
		}
	}

	/**
	 * 按照交易类型划分,重要交易，一般性交易。对应不同的主路径。
	 */
	public final static String type_business = "01";
	public final static String type_certificate = "02";

	/**
	 * 按照操作模块划分。对应不同的Class。
	 */
	public final static String module_rsa = "001";
	public final static String module_init = "002";
	public final static String module_login = "003";
	public final static String module_homepage = "004";

	/**
	 * 按照操作类型划分,增删改查，以及其他。对应不同的function。
	 */
	public final static String code_find = "01";
	public final static String code_create = "02";
	public final static String code_update = "03";
	public final static String code_delete = "04";
	public final static String code_check = "05";

}
