package com.catherine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

public class KeystoreManager {
	private final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	private final String CHARSET = "UTF8";

	public void getKeyPairFromKeystore() throws FileNotFoundException, IOException, CertificateException,
			NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {

		KeyStore keystore = KeyStore.getInstance(KeySet.KEYSTORE_TYPE);
		File kf = new File(KeySet.KEYSTORE_PATH);
		// System.out.println(kf.getAbsolutePath());
		keystore.load(new FileInputStream(kf), KeySet.KEYSTORE_PW.toCharArray());
		Key key = keystore.getKey(KeySet.KEYSTORE_ALIAS, KeySet.KEY_PW.toCharArray());
		if (key instanceof PrivateKey) {

			// Get certificate of public key
			Certificate cert = keystore.getCertificate(KeySet.KEYSTORE_ALIAS);

			// Get public key
			PublicKey publicKey = cert.getPublicKey();

			// Return a key pair
			KeyPair kp = new KeyPair(publicKey, (PrivateKey) key);

			System.out.println("==>private key: " + bytesToHexString(kp.getPrivate().getEncoded()));
			System.out.println("==>public key: " + bytesToHexString(kp.getPublic().getEncoded()));
		}
	}

	/**
	 * 用keystore生成的证书提取公钥加密
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
	public byte[] encrypt(String message)
			throws UnsupportedEncodingException, BadPaddingException, CertificateException, FileNotFoundException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {

		byte[] msg = message.getBytes(CHARSET); // 待加解密的消息
		// 用证书的公钥加密
		CertificateFactory cff = CertificateFactory.getInstance("X.509");
		FileInputStream fis1 = new FileInputStream(KeySet.CERTIFICATION_PATH); // 证书文件
		Certificate cf = cff.generateCertificate(fis1);
		PublicKey pk1 = cf.getPublicKey(); // 得到证书文件携带的公钥
		Cipher c1 = Cipher.getInstance(TRANSFORMATION); // 定义算法：RSA
		c1.init(Cipher.ENCRYPT_MODE, pk1);
		byte[] msg1 = c1.doFinal(msg); // 加密后的数据
		return msg1;
	}

	/**
	 * 用同一keystore解密
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
	public String decrypt(byte[] message) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			IOException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 用证书的私钥解密 - 该私钥存在生成该证书的密钥库中
		FileInputStream fis = new FileInputStream(KeySet.KEYSTORE_PATH);
		KeyStore ks = KeyStore.getInstance(KeySet.KEYSTORE_TYPE);
		ks.load(fis, KeySet.KEYSTORE_PW.toCharArray());
		PrivateKey pk2 = (PrivateKey) ks.getKey(KeySet.KEYSTORE_ALIAS, KeySet.KEY_PW.toCharArray()); // 获取证书私钥
		fis.close();
		Cipher c2 = Cipher.getInstance(TRANSFORMATION);
		c2.init(Cipher.DECRYPT_MODE, pk2);
		byte[] msg2 = c2.doFinal(message);
		return new String(msg2, CHARSET);
	}

	private final String SINGLE_KEY_ALGORITHM = "DES";

	/**
	 * 生成对称密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public String generateKey() throws NoSuchAlgorithmException {
		KeyGenerator kGenerator = KeyGenerator.getInstance(SINGLE_KEY_ALGORITHM);
		// 设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。（也许是算法本身的限制，或者是不同Provider的限制，或者是政府管制的限制）
		kGenerator.init(56);
		SecretKey secretKey = kGenerator.generateKey();
		// 获取二进制的书面表达
		byte[] keyData = secretKey.getEncoded();
		// 日常使用时，一般会把上面的二进制数组通过Base64编码转换成字符串，然后发给使用者
		String keyInBase64 = Base64.getEncoder().encodeToString(keyData);
		System.out.println("==>secret key: (encrpted data) " + bytesToHexString(keyData));
		System.out.println("==>secret key: (keyInBase64) " + keyInBase64);
		System.out.println("==>secret key: (Algorithm) " + secretKey.getAlgorithm());
		return keyInBase64;
	}

	/**
	 * 获取对称key的字符串并还原成SecretKey
	 * 
	 * @param sectretKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void converStringToKey(String sectretKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 假设对方收到了base64编码后的密钥，首先要得到其二进制表达式
		byte[] tmp = Base64.getDecoder().decode(sectretKey);
		// 用二进制数组构造KeySpec对象。对称key使用SecretKeySpec类
		SecretKeySpec secretKeySpec = new SecretKeySpec(tmp, SINGLE_KEY_ALGORITHM);
		// 创建对称Key导入用的SecretKeyFactory
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(SINGLE_KEY_ALGORITHM);
		SecretKey secretKey = secretKeyFactory.generateSecret(secretKeySpec);
		// 获取二进制的书面表达
		byte[] keyData = secretKey.getEncoded();
		// 这里获取的值和通过上述generateKey()生成的值一样
		System.out.println("==>secret key: (decrpted data) " + bytesToHexString(keyData));
		System.out.println("==>secret key: (Algorithm) " + secretKey.getAlgorithm());
	}

	private final String KEYPAIR_ALGORITHM = "RSA";

	/**
	 * 生成非對稱密钥
	 * 
	 * @return RSARule
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws ClassNotFoundException
	 */
	public RSARule generateKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException {
		KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(KEYPAIR_ALGORITHM);
		// 限制长度
		kpGenerator.initialize(1024);
		// 创建非对称密钥对，即KeyPair对象
		KeyPair keyPair = kpGenerator.generateKeyPair();
		// 获取密钥对中的公钥和私钥对象
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		// 打印base64编码后的公钥和私钥值，每次都不一样
		System.out.println("==>public key: " + bytesToHexString(publicKey.getEncoded()));
		System.out.println("==>private key: " + bytesToHexString(privateKey.getEncoded()));

		// SecretKeySpec没有提供类似对称密钥的方法直接从二进制数值还原
		Class clazz = Class.forName("java.security.spec.RSAPublicKeySpec");
		KeyFactory kFactory = KeyFactory.getInstance(KEYPAIR_ALGORITHM);
		RSAPublicKeySpec rsaPublicKeySpec = (RSAPublicKeySpec) kFactory.getKeySpec(publicKey, clazz);
		// 对RSA算法来说，只要获取modulus和exponent这两个RSA算法特定的参数就可以了
		RSARule rule = new RSARule();
		rule.setModulus(rsaPublicKeySpec.getModulus());
		rule.setExponent(rsaPublicKeySpec.getPublicExponent());

		System.out.println("==>rsa public Key spec: (modulus)" + bytesToHexString(rule.getModulus().toByteArray()));
		System.out.println("==>rsa public Key spec: (exponent)" + bytesToHexString(rule.getExponent().toByteArray()));
		return rule;
	}

	/**
	 * 获取非对称密钥的公钥字符串并还原成PublicKey
	 * 
	 * @param modulus
	 * @param exponent
	 * @throws ClassNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void converStringToPublicKey(RSARule rule)
			throws ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] modulusByteArry = rule.getModulus().toByteArray();
		byte[] exponentByteArry = rule.getExponent().toByteArray();

		// 由接收到的参数构造RSAPublicKeySpec对象
		RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(new BigInteger(modulusByteArry),
				new BigInteger(exponentByteArry));
		// 根据RSAPublicKeySpec对象获取公钥对象
		KeyFactory kFactory = KeyFactory.getInstance(KEYPAIR_ALGORITHM);
		PublicKey publicKey = kFactory.generatePublic(rsaPublicKeySpec);
		System.out.println("==>public key: " + bytesToHexString(publicKey.getEncoded()));

	}

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	private final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public class RSARule {
		private BigInteger modulus;
		private BigInteger exponent;

		public BigInteger getModulus() {
			return modulus;
		}

		public void setModulus(BigInteger modulus) {
			this.modulus = modulus;
		}

		public BigInteger getExponent() {
			return exponent;
		}

		public void setExponent(BigInteger exponent) {
			this.exponent = exponent;
		}

	}
}
