package per.rss.core.base.util;

import com.alibaba.fastjson.JSONObject;

public class StringUtils {
	/**
	 * 如果这个字符串是null值或空字符串，则返回true
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(String val){
		return org.apache.commons.lang3.StringUtils.isEmpty(val);
	}
	
	/**
	 * 将obj参数使用json格式进行转换
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJSONString(Object obj){
		return JSONObject.toJSONString(obj);
	}
}
