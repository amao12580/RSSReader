package per.rss.core.base.util;

import com.google.gson.Gson;

public class StringUtils {
	private final static Gson gson = new Gson();

	/**
	 * 如果这个字符串是null值或空字符串，则返回true
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(String val) {
		return org.apache.commons.lang3.StringUtils.isEmpty(val);
	}

	/**
	 * 将obj参数使用json格式进行转换
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJSONString(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * 将json格式的字符串转换为对象
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T  formatJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	
	/**
	 * 验证字符串是否为数字格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
