package com.catherine.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
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

public class KeystoreManager {
	private final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	private final String CHARSET = "UTF8";

	public void generateKeyPair() {
		try {
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

				System.out.println("private key:" + kp.getPrivate());
				System.out.println("public key:" + kp.getPublic());
			}
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}

	public byte[] encrypty(String message)
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

	public String decrypty(byte[] message) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
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
}
