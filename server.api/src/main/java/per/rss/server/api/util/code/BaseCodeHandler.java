package per.rss.server.api.util.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseCodeHandler {
	private static final Logger logger = LoggerFactory.getLogger(BaseCodeHandler.class);

	public static String encrypt(String Plaintext, Object Keys) {
		logger.debug("错误的调用父类：Plaintext=" + Plaintext + ",Keys=" + Keys.toString());
		return null;
	}

	public static String encrypt(String Plaintext) {
		logger.debug("错误的调用父类：Plaintext=" + Plaintext);
		return null;
	}

	public static String decrypt(String Ciphertext, Object Keys) {
		logger.debug("错误的调用父类：Ciphertext=" + Ciphertext + ",Keys=" + Keys.toString());
		return null;
	}

	public static String decrypt(String Ciphertext) {
		logger.debug("错误的调用父类：Ciphertext=" + Ciphertext);
		return null;
	}
	
	public static void main(String[] args) {
		SubClass sc = new SubClass();
        sc.printX();
	}
}
