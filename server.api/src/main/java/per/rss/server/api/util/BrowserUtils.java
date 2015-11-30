package per.rss.server.api.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import per.rss.core.base.util.CollectionUtils;
import per.rss.server.api.bo.core.Browser;

/**
 * 浏览器工具类
 */
public class BrowserUtils {
	private static Map<String, Set<String>> browsers;

	static {
		if (CollectionUtils.isEmpty(browsers)) {
			browsers = new HashMap<String, Set<String>>(2);
			String type1 = "chrome";
			Set<String> version11 = new HashSet<>(2);
			version11.add("46.0.2490.71");
			version11.add("46.0.2490.80");

			browsers.put(type1, version11);

			String type2 = "firefox";
			Set<String> version21 = new HashSet<>(2);
			version21.add("41.0");
			version21.add("42.0");

			browsers.put(type2, version21);
		}
	}

	/**
	 * 获取浏览器类型及 版本
	 * 
	 * @param req
	 * @return
	 */
	public static Browser getUserAgent(HttpServletRequest req) {
		Map<String, String> Sys = new HashMap<String, String>(2);
		String ua = req.getHeader("User-Agent").toLowerCase();
		String s;
		String msieP = "msie ([\\d.]+)";
		String firefoxP = "firefox\\/([\\d.]+)";
		String chromeP = "chrome\\/([\\d.]+)";
		String operaP = "opera.([\\d.]+)/)";
		String safariP = "version\\/([\\d.]+).*safari";

		Pattern pattern = Pattern.compile(msieP);
		Matcher mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			Sys.put("type", "ie");
			Sys.put("version", s.split(" ")[1]);
			return Browser.toBrowser(Sys);
		}
		pattern = Pattern.compile(firefoxP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			Sys.put("type", "firefox");
			Sys.put("version", s.split("/")[1]);
			return Browser.toBrowser(Sys);
		}
		pattern = Pattern.compile(chromeP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			Sys.put("type", "chrome");
			Sys.put("version", s.split("/")[1]);
			return Browser.toBrowser(Sys);
		}
		pattern = Pattern.compile(operaP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			Sys.put("type", "opera");
			Sys.put("version", s.split("\\.")[1]);
			return Browser.toBrowser(Sys);
		}
		pattern = Pattern.compile(safariP);
		mat = pattern.matcher(ua);
		if (mat.find()) {
			s = mat.group();
			Sys.put("type", "safari");
			Sys.put("version", s.split("/")[1].split(".")[0]);
			return Browser.toBrowser(Sys);
		}
		return Browser.toBrowser(Sys);
	}

	public static boolean checkSupport(Browser b) {
		if (b == null) {
			return false;
		}
		if (!CollectionUtils.isEmpty(browsers)) {
			String type = b.getType();
			if (browsers.keySet().contains(type)) {
				if (browsers.get(type).contains(b.getVersion())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
