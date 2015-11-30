package per.rss.server.api.util.aes;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import per.rss.core.base.util.StringUtils;
import per.rss.core.base.util.UUIDUtils;

/**
 * http://aub.iteye.com/blog/1133494
 * 
 * http://sunjiangwei.iteye.com/blog/1848928
 * 
 * http://alleni123.iteye.com/blog/2076721
 * 
 * AES Coder<br/>
 * secret key length: 128bit, default: 128 bit<br/>
 * mode: ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding: Nopadding/PKCS5Padding/ISO10126Padding/
 * 
 * @author Aub
 * 
 */
public class AESUtil {

	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 初始化密钥
	 * 
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] initSecretKey() {
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
		// 初始化此密钥生成器，使其具有确定的密钥大小
		// AES 要求密钥长度为 128
		kg.init(128);
		// 生成一个密钥
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 将十六进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成十六进制
	 * 
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return 密钥
	 */
	public static Key toKey(byte[] key) {
		// 生成密钥
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	/**
	 * 生成一个固定密钥
	 * 
	 * @param password
	 *            长度必须是16的倍数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static Key toKey(String password) throws UnsupportedEncodingException {
		return new SecretKeySpec(password.getBytes("UTF-8"), KEY_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            二进制密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) {
		try {
			// 实例化
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			// 使用密钥初始化，设置为加密模式
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 执行操作
			return cipher.doFinal(data);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content, String password) {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		try {
			// 生成密钥
			Key key = toKey(password);
			// 将String转换为二进制
			byte[] byteContent = content.getBytes("UTF-8");
			// 创建密码器
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 初始化为加密模式
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 执行加密加密
			byte[] result = cipher.doFinal(byteContent);

			return parseByte2HexStr(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String content, String password) {
		if (StringUtils.isEmpty(content)) {
			return "";
		}

		try {
			// 将十六进制转换为二进制
			byte[] contentHex = parseHexStr2Byte(content);

			// 生成密钥
			Key key = toKey(password);
			// 创建密码器
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			// 初始化解码模式
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 解密
			byte[] result = cipher.doFinal(contentHex);

			return new String(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            二进制密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	public static String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
//		byte[] key = initSecretKey();
//		System.out.println("key：" + showByteArray(key));
//
//		Key k = toKey(key);
//
//		// String data1 = "AES数据";
//
//		LoginSuccessCookieBo bo = new LoginSuccessCookieBo();
//		bo.setUid("10000");
//		bo.seteTime(1444963798245l);
//		bo.setIp("192.168.0.158");
//		String data = StringUtils.toJSONString(bo);
//		String keyString = StringUtils.toJSONString(k);
//
//		System.out.println("key: string:" + keyString);
//		System.out.println("加密前数据: string:" + data);
//		System.out.println("加密前数据: byte[]:" + showByteArray(data.getBytes()));
//		System.out.println();
//		byte[] encryptData = encrypt(data.getBytes(), k);
//		System.out.println("加密后数据: byte[]:" + showByteArray(encryptData));
//		System.out.println("加密后数据: hexStr:" + Hex.encode(encryptData));
//		System.out.println();
//
//		byte[] decryptData = decrypt(encryptData, k);
//		System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
//		System.out.println("解密后数据: string:" + new String(decryptData));

		// 实例二
		String content = "TGT-1-XDm5ultp4XEgb69cDebwRFWKPwEe158AF300DF0odckQniqHlJN";
		// 加密
		System.out.println("加密前：" + content);
		String password=UUIDUtils.randomUUID();
//		String password="1234567890123456";
		System.out.println("密钥长度：" + password.length());
		String encryptResultStr = encrypt(content, password);
		System.out.println("加密后：" + encryptResultStr);
		// 解密
		System.out.println("解密后：" + decrypt(encryptResultStr, password));
	}
}