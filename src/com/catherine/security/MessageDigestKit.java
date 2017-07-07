package com.catherine.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import javax.crypto.Mac;

/**
 * Message digest
 * 消息摘要，通过MD或SHA的算法计算出一个文件的消息摘要，每个文件的消息摘要都不同，一旦改了一个字就会不一样。用来防止文件被修改。<br>
 * <br>
 * MD值其实并不能真正解决数据被篡改的问题。因为作假者可以搞一个假网站，然后提供
 * 假数据和根据假数据得到的MD值。这样，下载者下载到假数据，计算的MD值和假网站提供的 MD数据确实一样，但是这份数据是被篡改过了的。<br>
 * 解决这个问题的一种方法是：计算MD的时候，输入除了消息数据外，还有一个密钥。<br>
 * 由于作假者没有密钥信息，所以它在假网站上上提供的MD肯定会和数据下载者根据密钥+假 数据得到的MD值不一样。<br>
 * 这种方法得到的MD叫Message Authentication Code，简称MAC
 * 
 * @author Catherine
 *
 */
public class MessageDigestKit {
	private final static byte[] SALT = "Cv30x4m".getBytes();

	/**
	 * 计算内容的消息摘要
	 * 
	 * @param content
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String doMD5(String content) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("md5");
		byte[] messageBytes = messageDigest.digest(content.getBytes());// 取得当前文件的MD5
		return bytesToHexString(messageBytes);
	}

	/**
	 * 安全的计算内容的消息摘要<br>
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String doMD5Safely(String path) throws IOException, NoSuchAlgorithmException {
		File file = new File(path);
		if (!file.exists())
			return "";

		MessageDigest messageDigest = MessageDigest.getInstance("md5");
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;// 读到末尾
		while ((len = fis.read(buffer)) != -1) {// 说明没有读到流的末尾
			messageDigest.update(buffer, 0, len);
		}

		byte[] messageBytes = messageDigest.digest();// 取得当前文件的MD5
		return bytesToHexString(messageBytes);
	}

	/**
	 * 安全的计算内容的消息摘要并加盐处理。<br>
	 * MessageDigest.digest会一次转换，但是遇到大型文件（比如一个G）无法一次转换，因为内存不足，
	 * 所以会用较安全的方法MessageDigest.update批次计算。
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String doSaltMD5Safely(String path) throws IOException, NoSuchAlgorithmException {
		File file = new File(path);
		if (!file.exists())
			return "";

		MessageDigest messageDigest = MessageDigest.getInstance("md5");
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;// 读到末尾
		while ((len = fis.read(buffer)) != -1) {// 说明没有读到流的末尾
			messageDigest.update(SALT);// 加盐
			messageDigest.update(buffer, 0, len);
		}

		byte[] messageBytes = messageDigest.digest();// 取得当前文件的MD5
		return bytesToHexString(messageBytes);
	}

	/**
	 * 把消息摘要用私钥生成一个签名，之后用此签名对文件进行验证。
	 * 
	 * @param path
	 * @param alg
	 *            签名算法
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws IOException
	 */
	public static byte[] signFiles(String path, String alg, PrivateKey privateKey)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Signature signature = Signature.getInstance(alg);
		// 计算签名时，需要调用initSign，并传入一个私钥
		signature.initSign(privateKey);

		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[1024];
		int len = -1;// 读到末尾
		while ((len = fis.read(buffer)) != -1) {// 说明没有读到流的末尾
			signature.update(buffer, 0, len);
		}
		// 得到签名值
		byte[] signatureBytes = signature.sign();
		return signatureBytes;
	}

	/**
	 * 签名
	 * 
	 * @param data
	 *            欲签名字串
	 * @param alg
	 *            签名算法
	 * @param privateKey
	 *            私钥
	 * @return signature 签名
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static byte[] sign(byte[] data, String alg, PrivateKey privateKey)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature sig = Signature.getInstance(alg);
		sig.initSign(privateKey);
		sig.update(data);
		return sig.sign();
	}

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param alg
	 * @param publicKey
	 * @return
	 */
	public static boolean verifySignature(byte[] signature, String alg, PublicKey publicKey, byte[] content)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature sig = Signature.getInstance(alg);
		sig.initVerify(publicKey);
		sig.update(content);
		return sig.verify(signature);
	}

	/**
	 * 签名时用KeyPair的私钥，验证时则是用该KeyPair的公钥。
	 * 
	 * @param signature
	 * @param alg
	 * @param path
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws IOException
	 */
	public static boolean verifyFileSignature(byte[] signature, String alg, String path, PublicKey publicKey)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Signature sig = Signature.getInstance(alg);
		// 校验时候需要调用initVerify，并传入公钥对象
		sig.initVerify(publicKey);

		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[1024];
		int len = -1;// 读到末尾
		while ((len = fis.read(buffer)) != -1) {// 说明没有读到流的末尾
			sig.update(buffer, 0, len);
		}
		return sig.verify(signature);
	}

	/**
	 * 创建MAC计算对象(这种方法得到的消息摘要叫Message Authentication
	 * Code，简称MAC)，其常用算法有“HmacSHA1”和“HmacMD5”。其中SHA1和MD5是
	 * 计算消息摘要的算法，Hmac是生成MAC码的算法。
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static String doHmacMD5(String content, Key key) throws NoSuchAlgorithmException, InvalidKeyException {
		Mac myMac = Mac.getInstance("HmacMD5");
		// 用密钥初始化MAC对象
		myMac.init(key);
		myMac.update(content.getBytes());
		// 得到最后的MAC值
		byte[] macBytes = myMac.doFinal();
		return bytesToHexString(macBytes);
	}

	/**
	 * 创建MAC计算对象(这种方法得到的消息摘要叫Message Authentication
	 * Code，简称MAC)，其常用算法有“HmacSHA1”和“HmacMD5”。其中SHA1和MD5是
	 * 计算消息摘要的算法，Hmac是生成MAC码的算法。
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 */
	public static String doHmacMD5Safely(String path, Key key)
			throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		File file = new File(path);
		if (!file.exists())
			return "";

		Mac myMac = Mac.getInstance("HmacMD5");
		// 用密钥初始化MAC对象
		myMac.init(key);

		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;// 读到末尾
		while ((len = fis.read(buffer)) != -1) {// 说明没有读到流的末尾
			myMac.update(buffer, 0, len);
		}
		// 得到最后的MAC值
		byte[] macBytes = myMac.doFinal();
		return bytesToHexString(macBytes);
	}

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	private final static String bytesToHexString(byte[] bArray) {
		// 将byte数组转换成十六进制的字符串
		StringBuffer sb = new StringBuffer();
		// 把每一个byte做一个与运算 0xff
		for (byte b : bArray) {
			// 与运算
			int num = b & 0xff;
			String str = Integer.toHexString(num);
			if (str.length() == 1) {
				// 长度为1时前面补0
				sb.append("0");
			}
			sb.append(str);
		}
		return sb.toString();
	}

	public static String byteArrayToString(byte[] bytes) {
		bytes = Base64.getEncoder().encode(bytes);
		return new String(bytes);
	}

	public static byte[] StringToByteArray(String string) {
		byte[] bytes = string.getBytes();
		return Base64.getDecoder().decode(bytes);
	}
}
