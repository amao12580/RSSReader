package per.rss.core.base.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger{
	
	public static Logger getLogger(Class<?> c){
		return LoggerFactory.getLogger(c);
	}
}
