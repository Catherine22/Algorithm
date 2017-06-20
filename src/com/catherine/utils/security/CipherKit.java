package com.catherine.utils.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/**
 * TRANSFORMATION = "算法／反馈模式／填充模式"<br>
 * 常见的算法有“DES”，“DESede”、“PBEWithMD5AndDES”、“Blowfish”。<br>
 * 常见的反馈模式有“ECB”、“CBC”、“CFB”、“OFB”、“PCBC”。 <br>
 * 常见的填充模式有“PKCS5Padding”、“NoPadding”。 <br>
 * 注意，算法、反馈模式和填充模式不是任意组合的。
 * 
 * @author Catherine
 *
 */
public class CipherKit {

	/**
	 * 用KeyStore生成的证书提取选取的公钥加密。
	 * 
	 * 
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws BadPaddingException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] encrypt(String message)
			throws UnsupportedEncodingException, BadPaddingException, CertificateException, FileNotFoundException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {

		byte[] msg = message.getBytes(Algorithm.CHARSET); // 待加解密的消息
		// 用证书的公钥加密
		CertificateFactory cff = CertificateFactory.getInstance("X.509");
		FileInputStream fis1 = new FileInputStream(KeySet.CERTIFICATION_PATH); // 证书文件
		Certificate cf = cff.generateCertificate(fis1);
		PublicKey pk1 = cf.getPublicKey(); // 得到证书文件携带的公钥
		Cipher c1 = Cipher.getInstance(Algorithm.rules.get(Algorithm.KEYPAIR_ALGORITHM)); // 定义算法：RSA
		c1.init(Cipher.ENCRYPT_MODE, pk1);
		byte[] msg1 = c1.doFinal(msg); // 加密后的数据
		return msg1;
	}

	/**
	 * 用密钥库提取选取的私钥解密。
	 * 
	 * @param message
	 * @return
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String decrypt(byte[] message) throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 用证书的私钥解密 - 该私钥存在生成该证书的密钥库（keystore）中
		FileInputStream fis = new FileInputStream(KeySet.KEYSTORE_PATH);
		KeyStore ks = KeyStore.getInstance(KeySet.KEYSTORE_TYPE);
		ks.load(fis, KeySet.KEYSTORE_PW.toCharArray());
		PrivateKey pk2 = (PrivateKey) ks.getKey(KeySet.KEYSTORE_ALIAS, KeySet.KEY_PW.toCharArray()); // 获取证书私钥
		fis.close();
		Cipher c2 = Cipher.getInstance(Algorithm.rules.get(Algorithm.KEYPAIR_ALGORITHM)); // 定义算法：RSA
		c2.init(Cipher.DECRYPT_MODE, pk2);
		byte[] msg2 = c2.doFinal(message);
		return new String(msg2, Algorithm.CHARSET);
	}

	/**
	 * 用对称密钥加密
	 * 
	 * @param key
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static DESRule encrypt(Key key, String message)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] msg = message.getBytes(Algorithm.CHARSET); // 待加解密的消息
		String type = key.getAlgorithm().toUpperCase();
		Cipher c1 = Cipher.getInstance(Algorithm.rules.get(type)); // 创建一个Cipher对象，注意这里用的算法需要和Key的算法匹配
		c1.init(Cipher.ENCRYPT_MODE, key);
		if (type.equals("DES")) {
			DESRule rule = new DESRule();
			byte[] decryptedData = c1.doFinal(msg);
			rule.setMessage(decryptedData); // 加密后的数据
			rule.setIv(c1.getIV()); // 获取本次加密时使用的初始向量。初始向量属于加密算法使用的一组参数。使用不同的加密算法时，需要保存的参数不完全相同。Cipher会提供相应的API
			return rule;
		} else
			return null;
	}

	/**
	 * 解密时，需要把加密后的数据，密钥和初始向量发给解密方。<br>
	 * 再次强调，不同算法加解密时，可能需要加密对象当初加密时使用的其他算法参数
	 * 
	 * @param key
	 * @param rule
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public static String decrypt(Key key, DESRule rule)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		String type = key.getAlgorithm().toUpperCase();
		Cipher c2 = Cipher.getInstance(Algorithm.rules.get(type)); // 创建一个Cipher对象，注意这里用的算法需要和Key的算法匹配
		if (type.equals("DES")) {
			IvParameterSpec ips = new IvParameterSpec(rule.getIv());
			c2.init(Cipher.DECRYPT_MODE, key, ips); // 设置Cipher为解密工作模式，需要把Key和算法参数传进去
			byte[] decryptedData = c2.doFinal(rule.getMessage());
			return new String(decryptedData, Algorithm.CHARSET);
		} else
			return null;
	}
}
