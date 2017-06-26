package com.catherine.utils.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
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
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Enumeration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 * Java Cryptography Extension（简写为JCE），JCE所包含的内容有加解密，密钥交换，消息摘要（Message
 * Digest，比如MD5等），密钥管理等。<br>
 * <br>
 * 创建一个KeySet类，格式同{@link com.catherine.utils.security.KeySetTemplate}
 * 
 * @author Catherine
 *
 */
public class KeystoreManager {

	/**
	 * KeyStore里边存储的东西可分为两种类型——Key Entry(KE)和Certificate Entry(CE)。<br>
	 * KE可携带KeyPair，或者SecretKey信息。如果KE存储的是KeyPair的话，它可能会携带一整条证书链信息。 <br>
	 * CE用于存储根证书。根证书只包含公钥。而且CE一般对应的是可信任的CA证书，即顶级CA的证书。这些证书存储在xxx/cacerts目录下。
	 * 
	 * 
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void printKeyStoreInfo() throws UnrecoverableKeyException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException {
		KeyStore keystore = KeyStore.getInstance(KeySet.KEYSTORE_TYPE);
		File kf = new File(KeySet.KEYSTORE_PATH);
		// System.out.println(kf.getAbsolutePath());
		keystore.load(new FileInputStream(kf), KeySet.KEYSTORE_PW.toCharArray());

		System.out.println("==>KeyStoreInfo: START");
		// 获取KeyStore中定义的别名
		Enumeration<String> aliasEnum = keystore.aliases();
		while (aliasEnum.hasMoreElements()) {
			String alias = aliasEnum.nextElement();
			// 判断别名对应的项是CE还是KE。注意,CE对应的是可信任CA的根证书。
			boolean bCE = keystore.isCertificateEntry(alias);
			boolean bKE = keystore.isKeyEntry(alias);
			// 本例中，存储的是KE信息
			System.out.println("==>KeyStoreInfo: (alias) " + alias + " (is CE) " + bCE + " (is KE) " + bKE);
			// 从KeyStore中取出别名对应的证书链
			Certificate[] certificates = keystore.getCertificateChain(alias);

			// 打印证书链的信息
			for (Certificate cert : certificates) {
				X509Certificate myCert = (X509Certificate) cert;
				System.out.println("I am a certificate:");
				System.out.println("Subjecte DN:" + myCert.getSubjectDN().getName());
				System.out.println("Issuer DN:" + myCert.getIssuerDN().getName());
				System.out.println(
						"Public Key:" + Base64.getEncoder().encodeToString(myCert.getPublicKey().getEncoded()));
			}
			// 取出别名对应的Key信息，一般取出的是私钥或者SecretKey。
			// 注意，不同的别名可能对应不同的Entry。本例中，KE和CE都使用一样的别名
			Key myKey = keystore.getKey(alias, KeySet.KEY_PW.toCharArray());
			if (myKey instanceof PrivateKey) {
				System.out.println("I am a private key:" + Base64.getEncoder().encodeToString(myKey.getEncoded()));
			} else if (keystore instanceof SecretKey) {
				if (keystore instanceof PrivateKey) {
					System.out.println("I am a secret key:" + Base64.getEncoder().encodeToString(myKey.getEncoded()));
				}
			}
		}
		System.out.println("==>KeyStoreInfo: END");

	}

	/**
	 * X.509是数字证书的规范，X.509只能携带公钥信息。<br>
	 * 另外还有PKCS#7和12包含更多的一些信息。PKCS#12由于可包含私钥信息，而且文件本身还可通过密码保护，所以更适合信息交换。<br>
	 * 常见的证书文件格式：".pem", ".cer", ".crt", ".der", ".p7b", ".p7c", ".p12"<br>
	 * 总结：<br>
	 * 1. 证书包含很多信息，但一般就是各种Key的内容。<br>
	 * 2. 证书由CA签发。为了校验某个证书是否可信，往往需要把一整条证书链都校验一把，直到根证书。<br>
	 * 3. 系统一般会集成很多根证书，这样免得使用者自己去下载根证书了。<br>
	 * 4. 证书自己的格式通用为X.509，但是证书文件的格式却有很多。不同的系统可能支持不同的证书文件。
	 * 
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 */
	public static void printCertificatesInfo() throws CertificateException, FileNotFoundException {
		CertificateFactory cff = CertificateFactory.getInstance("X.509");
		FileInputStream fis1 = new FileInputStream(KeySet.CERTIFICATION_PATH); // 证书文件
		X509Certificate cf = (X509Certificate) cff.generateCertificate(fis1);

		System.out.println("==>X509Certificate: " + cf.toString());
	}

	/**
	 * KeyStore就是一个容器，存了一堆key，JCE提供了KeyStore的API让我们可以操作、管理那些key
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 */
	public static void getKeyPairFromKeystore() throws FileNotFoundException, IOException, CertificateException,
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

			System.out.println("==>private key: " + Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded()));
			System.out.println("==>public key: " + Base64.getEncoder().encodeToString(kp.getPublic().getEncoded()));
		}
	}

	/**
	 * 生成对称密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static Key generateKey() throws NoSuchAlgorithmException {
		KeyGenerator kGenerator = KeyGenerator.getInstance(Algorithm.SINGLE_KEY_ALGORITHM);
		// 设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。（也许是算法本身的限制，或者是不同Provider的限制，或者是政府管制的限制）
		kGenerator.init(56);
		SecretKey secretKey = kGenerator.generateKey();
		return secretKey;
	}

	/**
	 * 生成对称密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static String generateKeyString() throws NoSuchAlgorithmException {
		KeyGenerator kGenerator = KeyGenerator.getInstance(Algorithm.SINGLE_KEY_ALGORITHM);
		// 设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。（也许是算法本身的限制，或者是不同Provider的限制，或者是政府管制的限制）
		kGenerator.init(56);
		SecretKey secretKey = kGenerator.generateKey();
		// 获取二进制的书面表达
		byte[] keyData = secretKey.getEncoded();
		// 日常使用时，一般会把上面的二进制数组通过Base64编码转换成字符串，然后发给使用者
		String keyInBase64 = Base64.getEncoder().encodeToString(keyData);
		System.out.println("==>secret key: (encrypted) " + keyInBase64);
		System.out.println("==>secret key: (Algorithm) " + secretKey.getAlgorithm());
		return keyInBase64;
	}

	/**
	 * 获取对称key的字符串并还原成SecretKey
	 * 
	 * @param secretKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static Key converStringToKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 假设对方收到了base64编码后的密钥，首先要得到其二进制表达式
		byte[] tmp = Base64.getDecoder().decode(secretKey);
		// 用二进制数组构造KeySpec对象。对称key使用SecretKeySpec类
		SecretKeySpec secretKeySpec = new SecretKeySpec(tmp, Algorithm.SINGLE_KEY_ALGORITHM);
		// 创建对称Key导入用的SecretKeyFactory
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(Algorithm.SINGLE_KEY_ALGORITHM);
		SecretKey sk = secretKeyFactory.generateSecret(secretKeySpec);
		// 获取二进制的书面表达
		byte[] keyData = sk.getEncoded();
		// 这里获取的值和通过上述generateKey()生成的值一样
		String keyInBase64 = Base64.getEncoder().encodeToString(keyData);
		System.out.println("==>secret key: (decrpted data) " + keyInBase64);
		System.out.println("==>secret key: (Algorithm) " + sk.getAlgorithm());
		return sk;
	}

	/**
	 * 生成非對稱密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(Algorithm.KEYPAIR_ALGORITHM);
		// 限制长度
		kpGenerator.initialize(1024);
		// 创建非对称密钥对，即KeyPair对象
		KeyPair keyPair = kpGenerator.generateKeyPair();
		return keyPair;
	}

	/**
	 * 生成非對稱密钥
	 * 
	 * @param callback
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws ClassNotFoundException
	 */
	public static void generateRSAKeyPair(RSACallback callback)
			throws NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException {
		KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance(Algorithm.KEYPAIR_ALGORITHM);
		// 限制长度
		kpGenerator.initialize(1024);
		// 创建非对称密钥对，即KeyPair对象
		KeyPair keyPair = kpGenerator.generateKeyPair();
		// 获取密钥对中的公钥和私钥对象
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		// 打印base64编码后的公钥和私钥值，每次都不一样
		System.out.println("==>public key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		System.out.println("==>private key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

		// SecretKeySpec没有提供类似对称密钥的方法直接从二进制数值还原
		Class clazz = Class.forName("java.security.spec.RSAPublicKeySpec");
		KeyFactory kFactory = KeyFactory.getInstance(Algorithm.KEYPAIR_ALGORITHM);
		RSAPublicKeySpec rsaPublicKeySpec = (RSAPublicKeySpec) kFactory.getKeySpec(publicKey, clazz);
		// 对RSA算法来说，只要获取modulus和exponent这两个RSA算法特定的参数就可以了
		String modulus = Base64.getEncoder().encodeToString(rsaPublicKeySpec.getModulus().toByteArray());
		String exponent = Base64.getEncoder().encodeToString(rsaPublicKeySpec.getPublicExponent().toByteArray());

		System.out.println("==>rsa public Key spec: (modulus)" + modulus);
		System.out.println("==>rsa public Key spec: (exponent)" + exponent);

		callback.onResponse(privateKey);
		callback.onResponse(modulus, exponent);
	}

	/**
	 * 获取非对称密钥的modulus和exponent并还原成PublicKey
	 * 
	 * @param modulus
	 * @param exponent
	 * @throws ClassNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static Key converStringToPublicKey(String modulus, String exponent)
			throws ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] modulusByteArry = Base64.getDecoder().decode(modulus);
		byte[] exponentByteArry = Base64.getDecoder().decode(exponent);

		// 由接收到的参数构造RSAPublicKeySpec对象
		RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(new BigInteger(modulusByteArry),
				new BigInteger(exponentByteArry));
		// 根据RSAPublicKeySpec对象获取公钥对象
		KeyFactory kFactory = KeyFactory.getInstance(Algorithm.KEYPAIR_ALGORITHM);
		PublicKey publicKey = kFactory.generatePublic(rsaPublicKeySpec);
		System.out.println("==>public key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		return publicKey;
	}
}
