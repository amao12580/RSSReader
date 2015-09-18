package per.rss.server.poll.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLHander {
	private static final Logger logger = LoggerFactory.getLogger(XMLHander.class);
	
	public static boolean validator(String xml){
		boolean result=false;
		Document document=null;
		try{
			document= DocumentHelper.parseText(xml);
			if (document == null) {
				logger.error("document 对象 创建失败.");
			}
			result=true;
		}catch(DocumentException e){
			logger.error("validator is error.DocumentException",e);
		}finally {
			if(document!=null){
				document=null;
			}
		}
		return result;
	}
}
