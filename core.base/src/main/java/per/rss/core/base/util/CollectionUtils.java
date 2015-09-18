package per.rss.core.base.util;

import java.util.List;
import java.util.Set;

public class CollectionUtils {
	public static boolean isEmpty(List<?> c){
		if(c==null){
			return true;
		}else if(c.size()<=0){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isEmpty(Set<?> c){
		if(c==null){
			return true;
		}else if(c.size()<=0){
			return true;
		}else{
			return false;
		}
	}
}
