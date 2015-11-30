package per.rss.server.api.util.rsa;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * 
 */
public class RSAUtil {
	private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

	public static KeyPair Bin2KeyPair(String data) {
		ByteArrayInputStream b = null;
		ObjectInputStream o = null;
		try {
			b = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
			o = new ObjectInputStream(new BufferedInputStream(b));
			Object obj = o.readObject();
			return ((KeyPair) obj);
		} catch (Exception e) {
			logger.error("Bin2KeyPair is error.", e);
			return null;
		} finally {
			if (b != null) {
				try {
					b.close();
				} catch (IOException e) {
					b = null;
					e.printStackTrace();
				}
			}
			if (o != null) {
				try {
					o.close();
				} catch (IOException e) {
					o = null;
					e.printStackTrace();
				}
			}
		}
	}

	public static String KeyPair2Bin(KeyPair kp) {
		ByteArrayOutputStream b = null;
		ObjectOutputStream o = null;
		try {
			b = new ByteArrayOutputStream();
			o = new ObjectOutputStream(b);
			o.writeObject(kp);
			return b.toString("ISO-8859-1");
		} catch (IOException e) {
			logger.error("KeyPair2Bin is error.", e);
			return null;
		} finally {
			if (b != null) {
				try {
					b.flush();
				} catch (IOException e1) {
					b = null;
					e1.printStackTrace();
				}
				try {
					b.close();
				} catch (IOException e) {
					b = null;
					e.printStackTrace();
				}
			}
			if (o != null) {
				try {
					o.flush();
				} catch (IOException e1) {
					o = null;
					e1.printStackTrace();
				}
				try {
					o.close();
				} catch (IOException e) {
					o = null;
					e.printStackTrace();
				}
			}
		}
	}

	// private static KeyPair getKeyPairByFile() throws Exception {
	// File file = new File(KeyPair_FilePath);
	// if (!file.exists()) {
	// logger.error("getKeyPairByFile file not exists.KeyPair_FilePath=" +
	// KeyPair_FilePath);
	// return null;
	// }
	// if (file.isDirectory()) {
	// logger.error("getKeyPairByFile file isDirectory.KeyPair_FilePath=" +
	// KeyPair_FilePath);
	// return null;
	// }
	// if (!file.isFile()) {
	// logger.error("getKeyPairByFile file is not a file.KeyPair_FilePath=" +
	// KeyPair_FilePath);
	// return null;
	// }
	// if (!file.canRead()) {
	// logger.error("getKeyPairByFile file is not can read.KeyPair_FilePath=" +
	// KeyPair_FilePath);
	// return null;
	// }
	// FileInputStream fis = new FileInputStream(KeyPair_FilePath);
	// ObjectInputStream oos = new ObjectInputStream(fis);
	// KeyPair kp = (KeyPair) oos.readObject();
	// oos.close();
	// fis.close();
	// return kp;
	// }
	//
	// private static void saveKeyPair(KeyPair kp) throws Exception {
	// File file = new File(KeyPair_FilePath);
	// if (file.exists() && !file.isDirectory() && file.isFile()) {
	// file.delete();
	// }
	// FileOutputStream fos = new FileOutputStream(KeyPair_FilePath);
	// ObjectOutputStream oos = new ObjectOutputStream(fos);
	// // 生成密钥
	// oos.writeObject(kp);
	// oos.close();
	// fos.close();
	// KeyPair = null;
	// }

	/**
	 * * 生成密钥对 *
	 * 
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
			final int KEY_SIZE = 2048;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// saveKeyPair(keyPair);
			return keyPair;
		} catch (Exception e) {
			logger.error("generateKeyPair is error.", e);
			return null;
		}
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	private static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	private static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param ciphertext
	 *            密文
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String ciphertext, RSAPrivateKey pk) throws Exception {
		byte[] en_result = new BigInteger(ciphertext, 16).toByteArray();
		byte[] de_result = RSAUtil.decrypt(pk, en_result);
		StringBuffer sb = new StringBuffer();
		sb.append(new String(de_result));
		return sb.reverse().toString();
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		ByteArrayOutputStream bout = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			bout = new ByteArrayOutputStream(64);
			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			if (bout != null) {
				bout.flush();
				bout.close();
			}
		}
	}

	/**
	 * * *
	 * 
	 * @param args
	 *            *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		KeyPair keyPair = RSAUtil.generateKeyPair();
		RSAPublicKey rsapk = (RSAPublicKey) keyPair.getPublic();
		System.out.println("getModulus:" + rsapk.getModulus());
		System.out.println("getPublicExponent:" + rsapk.getPublicExponent());

		String keyPairStr = KeyPair2Bin(keyPair);
		System.out.println("keyPairStr:" + keyPairStr);

		KeyPair myKeyPair = Bin2KeyPair(keyPairStr);
		RSAPublicKey myrsapk = (RSAPublicKey) myKeyPair.getPublic();
		System.out.println("getModulus:" + myrsapk.getModulus());
		System.out.println("getPublicExponent:" + myrsapk.getPublicExponent());

		// String test = "hello world";
		// byte[] en_test = encrypt(keyPair.getPublic(), test.getBytes());
		// StringBuffer sb = new StringBuffer();
		// sb.append(new String(en_test));
		// System.out.println(sb.reverse().toString());
		//
		// byte[] de_test = decrypt(keyPair.getPrivate(), en_test);
		// System.out.println(new String(de_test));
	}
}
