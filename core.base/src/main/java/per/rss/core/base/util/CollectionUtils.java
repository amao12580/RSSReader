package per.rss.core.base.util;

import java.util.Collection;

public class CollectionUtils {
	public static boolean isEmpty(Collection<?> coll){
		return org.apache.commons.collections.CollectionUtils.isEmpty(coll);
	}
}
