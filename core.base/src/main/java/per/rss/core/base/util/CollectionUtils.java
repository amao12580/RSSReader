package per.rss.core.base.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {
	public static boolean isEmpty(Collection<?> coll) {
		return org.apache.commons.collections.CollectionUtils.isEmpty(coll);
	}

	public static boolean isEmpty(Map<?, ?> coll) {
		if (coll == null) {
			return true;
		}
		if (coll.size() <= 0) {
			return true;
		}
		return false;
	}
}
