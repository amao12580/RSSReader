package per.rss.core.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 原始字符串，经MD5加密算法，加密为32位小写的字符串
 * @author cifpay
 *
 */
public class MD5Utils {
	private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);
	
	public static String code(String str){  
        String reStr = null;  
        try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            byte[] bytes = md5.digest(str.getBytes());  
            StringBuffer stringBuffer = new StringBuffer();  
            for (byte b : bytes){  
                int bt = b&0xff;  
                if (bt < 16){  
                    stringBuffer.append(0);  
                }   
                stringBuffer.append(Integer.toHexString(bt));  
            }  
            reStr = stringBuffer.toString();  
        } catch (NoSuchAlgorithmException e) {  
        	logger.error("MD5CodeUtil.code is exception.",e);  
        	return str;  
        }  
        return reStr;  
    }  
}
